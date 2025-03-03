import React, {useEffect, useState} from 'react';
import {Alert, Box, Button, Container, Grid, TextField, Typography,} from '@mui/material';
import {useDispatch} from 'react-redux';
import {addTeam} from '../../store/teamsSlice';

function CreateTeamForm() {
    const dispatch = useDispatch();
    const [formData, setFormData] = useState({
        name: '',
    });
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData((prev) => ({...prev, [name]: value}));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await dispatch(addTeam(formData)).unwrap();
            setFormData({name: ''});
            setError(null);
        } catch (err) {
            setError(err.message || 'An error occurred while creating the team.');
        }
    };

    return (
        <Container maxWidth="sm" sx={{
            border: '2px solid #1976d2',
            padding: 4,
            marginTop: 5,
            marginBottom: 5,
            borderRadius: 2,
        }}>
            <Box>
                <Typography variant="h4" gutterBottom>
                    Create Team
                </Typography>
                {error && (
                    <Alert severity="error" sx={{marginBottom: 2}}>
                        {error}
                    </Alert>
                )}
                <form onSubmit={handleSubmit}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                label="Name"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button variant="contained" type="submit" fullWidth>
                                Create Team
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Box>
        </Container>
    );
}

export default CreateTeamForm;
