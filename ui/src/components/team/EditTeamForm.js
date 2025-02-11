import React, { useState, useEffect } from 'react';
import {
    TextField,
    Button,
    Grid,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Alert,
} from '@mui/material';
import { useDispatch } from 'react-redux';
import { editTeam } from '../../store/teamsSlice';

function EditTeamForm({ open, onClose, team }) {
    const dispatch = useDispatch();
    const [formData, setFormData] = useState({
        name: '',
    });
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    useEffect(() => {
        if (team) {
            setFormData({
                name: team.name || '',
            });
        }
    }, [team]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await dispatch(editTeam({ id: team.id, updatedTeam: formData })).unwrap();
            setSuccess('Team updated successfully!');
            setError(null);
            onClose();
        } catch (err) {
            setError(err.message || 'An error occurred while updating the team.');
            setSuccess(null);
        }
    };

    if (!team) return null;

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <DialogTitle>Edit Team</DialogTitle>
            <DialogContent>
                {error && (
                    <Alert severity="error" sx={{ marginBottom: 2 }}>
                        {error}
                    </Alert>
                )}
                {success && (
                    <Alert severity="success" sx={{ marginBottom: 2 }}>
                        {success}
                    </Alert>
                )}
                <form onSubmit={handleSubmit}>
                    <Grid container spacing={2} sx={{ marginTop: 1 }}>
                        <Grid item xs={12}>
                            <TextField
                                label="Team Name"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                                fullWidth
                            />
                        </Grid>
                    </Grid>
                </form>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button variant="contained" onClick={handleSubmit}>
                    Save Changes
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default EditTeamForm;
