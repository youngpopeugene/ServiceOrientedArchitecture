import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { removeTeam } from '../../store/teamsSlice';
import { Button, Dialog, DialogTitle, DialogActions, Alert } from '@mui/material';

function DeleteTeamButton({ teamId }) {
    const dispatch = useDispatch();
    const [openConfirm, setOpenConfirm] = useState(false);
    const [error, setError] = useState(null);

    const handleDeleteClick = () => {
        setOpenConfirm(true);
    };

    const handleConfirm = async () => {
        try {
            await dispatch(removeTeam(teamId)).unwrap();
            alert('Team deleted successfully!');
            setError(null);
        } catch (err) {
            setError(err.message || 'An error occurred while deleting the team.');
        } finally {
            setOpenConfirm(false);
        }
    };

    const handleCancel = () => {
        setOpenConfirm(false);
    };

    return (
        <>
            <Button variant="outlined" color="error" onClick={handleDeleteClick} fullWidth sx={{ mt: 1 }}>
                Delete
            </Button>
            <Dialog open={openConfirm} onClose={handleCancel}>
                <DialogTitle>Are you sure you want to delete this Team?</DialogTitle>
                {error && (
                    <Alert severity="error" sx={{ margin: 2 }}>
                        {error}
                    </Alert>
                )}
                <DialogActions>
                    <Button onClick={handleCancel}>Cancel</Button>
                    <Button variant="contained" color="error" onClick={handleConfirm}>
                        Delete
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

export default DeleteTeamButton;
