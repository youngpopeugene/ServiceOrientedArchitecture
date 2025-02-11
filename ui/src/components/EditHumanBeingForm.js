import React, { useState, useEffect } from 'react';
import {
    TextField,
    Button,
    Grid,
    Select,
    MenuItem,
    InputLabel,
    FormControl,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    FormControlLabel,
    Checkbox,
    Alert,
} from '@mui/material';
import { useDispatch } from 'react-redux';
import { editHumanBeing } from '../store/humanBeingsSlice';
import { fetchEnums } from '../services/humanBeingService';

function EditHumanBeingForm({ open, onClose, humanBeing }) {
    const dispatch = useDispatch();
    const [formData, setFormData] = useState(humanBeing);
    const [error, setError] = useState(null);
    const [enums, setEnums] = useState({
        weaponTypes: [],
        moodTypes: [],
    });

    useEffect(() => {
        fetchEnums()
            .then((data) => setEnums(data))
            .catch((err) => setError(err));
    }, []);

    useEffect(() => {
        setFormData(humanBeing);
    }, [humanBeing]);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        if (name.startsWith('coordinates.')) {
            const coordField = name.split('.')[1];
            setFormData((prev) => ({
                ...prev,
                coordinates: {
                    ...prev.coordinates,
                    [coordField]: value,
                },
            }));
        } else if (name.startsWith('car.')) {
            const carField = name.split('.')[1];
            let carValue = value;
            if (type === 'checkbox') {
                carValue = checked;
            }
            setFormData((prev) => ({
                ...prev,
                car: {
                    ...prev.car,
                    [carField]: carValue,
                },
            }));
        } else if (type === 'checkbox') {
            setFormData((prev) => ({ ...prev, [name]: checked }));
        } else {
            setFormData((prev) => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await dispatch(editHumanBeing({ id: humanBeing.id, updatedHB: formData })).unwrap();
            alert('HumanBeing updated successfully!');
            setError(null);
            onClose();
        } catch (err) {
            console.error('Error updating human being:', err);
            setError(err);
        }
    };

    if (!formData) return null;

    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
            <DialogTitle>Edit HumanBeing</DialogTitle>
            <DialogContent>
                {error && (
                    <Alert severity="error" sx={{marginBottom: 2}}>{error.message || 'An error occurred'}</Alert>
                )}
                <form onSubmit={handleSubmit}>
                    <Grid container spacing={2} sx={{ marginTop: 1 }}>
                        <Grid item xs={12}>
                            <TextField
                                label="Name"
                                name="name"
                                value={formData.name || ''}
                                onChange={handleChange}
                                required
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                label="Coordinate X"
                                name="coordinates.x"
                                value={formData.coordinates?.x || ''}
                                onChange={handleChange}
                                required
                                fullWidth
                                type="number"
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                label="Coordinate Y"
                                name="coordinates.y"
                                value={formData.coordinates?.y || ''}
                                onChange={handleChange}
                                required
                                fullWidth
                                type="number"
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <FormControlLabel
                                control={<Checkbox checked={!!formData.realHero} onChange={handleChange} name="realHero" />}
                                label="Real Hero"
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <FormControlLabel
                                control={<Checkbox checked={!!formData.hasToothpick} onChange={handleChange} name="hasToothpick" />}
                                label="Has Toothpick"
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                label="Impact Speed"
                                name="impactSpeed"
                                value={formData.impactSpeed || ''}
                                onChange={handleChange}
                                fullWidth
                                type="number"
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                label="Soundtrack Name"
                                name="soundtrackName"
                                value={formData.soundtrackName || ''}
                                onChange={handleChange}
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                label="Minutes of Waiting"
                                name="minutesOfWaiting"
                                value={formData.minutesOfWaiting || ''}
                                onChange={handleChange}
                                fullWidth
                                type="number"
                                step="any"
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <FormControl fullWidth>
                                <InputLabel id="weapon-label">Weapon Type</InputLabel>
                                <Select
                                    labelId="weapon-label"
                                    name="weaponType"
                                    value={formData.weaponType || ''}
                                    onChange={handleChange}
                                    label="Weapon Type"
                                >
                                    {enums.weaponTypes.map((w) => (
                                        <MenuItem key={w} value={w}>
                                            {w}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={6}>
                            <FormControl fullWidth>
                                <InputLabel id="mood-label">Mood Type</InputLabel>
                                <Select
                                    labelId="mood-label"
                                    name="moodType"
                                    value={formData.moodType || ''}
                                    onChange={handleChange}
                                    label="Mood Type"
                                >
                                    {enums.moodTypes.map((m) => (
                                        <MenuItem key={m} value={m}>
                                            {m}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                label="Car Name"
                                name="car.name"
                                value={formData.car?.name || ''}
                                onChange={handleChange}
                                fullWidth
                            />
                            <FormControlLabel
                                control={<Checkbox checked={!!formData.car?.cool} onChange={handleChange} name="car.cool" />}
                                label="Car Cool?"
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                label="Team ID"
                                name="teamID"
                                value={formData.teamID || ''}
                                onChange={handleChange}
                                fullWidth
                                type="number"
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

export default EditHumanBeingForm;
