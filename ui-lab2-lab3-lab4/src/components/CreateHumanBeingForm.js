import React, {useEffect, useState} from 'react';
import {
    Alert,
    Box,
    Button,
    Checkbox,
    Container,
    FormControl,
    FormControlLabel,
    Grid,
    InputLabel,
    MenuItem,
    Select,
    TextField,
    Typography,
} from '@mui/material';
import {useDispatch} from 'react-redux';
import {addHumanBeing} from '../store/humanBeingsSlice';
import {fetchEnums} from '../services/humanBeingService';

function CreateHumanBeingForm() {
    const dispatch = useDispatch();
    const [formData, setFormData] = useState({
        name: '',
        coordinates: {x: '', y: ''},
        realHero: false,
        hasToothpick: false,
        impactSpeed: '',
        soundtrackName: '',
        minutesOfWaiting: '',
        weaponType: '',
        moodType: '',
        car: {name: '', cool: false},
        teamID: '',
    });
    const [error, setError] = useState(null);
    const [enums, setEnums] = useState({
        weaponTypes: [],
        moodTypes: [],
    });

    useEffect(() => {
        fetchEnums()
            .then((data) => {
                setEnums(data);
            })
            .catch((err) => {
                if (err.name !== "TypeError") setError(err);
            });
    }, []);

    const handleChange = (e) => {
        const {name, value, type, checked} = e.target;
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
            setFormData((prev) => ({...prev, [name]: checked}));
        } else {
            setFormData((prev) => ({...prev, [name]: value}));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await dispatch(addHumanBeing(formData)).unwrap();
            alert('HumanBeing created successfully!');
            setFormData({
                name: '',
                coordinates: {x: '', y: ''},
                realHero: false,
                hasToothpick: false,
                impactSpeed: '',
                soundtrackName: '',
                minutesOfWaiting: '',
                weaponType: '',
                moodType: '',
                car: {name: '', cool: false},
                teamID: '',
            });
            setError(null);
        } catch (err) {
            console.error('Error creating human being:', err);
            setError(err);
        }
    };

    return (
        <Container maxWidth="xl" sx={{
            border: '2px solid #1976d2',
            paddingBottom: 5,
            marginTop: 5,
            marginBottom: 5,
            borderRadius: 2,
        }}>
            <Box mt={4}>
                <Typography variant="h4" gutterBottom>
                    Create HumanBeing
                </Typography>
                {error && (
                    <Alert severity="error" sx={{marginBottom: 2}}>{error.message || 'An error occurred'}</Alert>
                )}
                <form onSubmit={handleSubmit}>
                    <Grid container spacing={1}>
                        <Grid item xs={4}>
                            <TextField
                                label="Name"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <TextField
                                label="Coordinate X"
                                name="coordinates.x"
                                value={formData.coordinates.x}
                                onChange={handleChange}
                                required
                                fullWidth
                                type="number"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <TextField
                                label="Coordinate Y"
                                name="coordinates.y"
                                value={formData.coordinates.y}
                                onChange={handleChange}
                                required
                                fullWidth
                                type="number"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <FormControlLabel
                                control={<Checkbox checked={formData.realHero} onChange={handleChange}
                                                   name="realHero"/>}
                                label="Real Hero"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <FormControlLabel
                                control={<Checkbox checked={formData.hasToothpick} onChange={handleChange}
                                                   name="hasToothpick"/>}
                                label="Has Toothpick"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <TextField
                                label="Impact Speed"
                                name="impactSpeed"
                                value={formData.impactSpeed}
                                onChange={handleChange}
                                fullWidth
                                required
                                type="number"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <TextField
                                label="Soundtrack Name"
                                name="soundtrackName"
                                value={formData.soundtrackName}
                                required
                                onChange={handleChange}
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <TextField
                                label="Minutes of Waiting"
                                name="minutesOfWaiting"
                                value={formData.minutesOfWaiting}
                                onChange={handleChange}
                                fullWidth
                                required
                                type="number"
                                step="any"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <FormControl fullWidth>
                                <InputLabel id="weapon-label">Weapon Type</InputLabel>
                                <Select
                                    labelId="weapon-label"
                                    name="weaponType"
                                    value={formData.weaponType}
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
                        <Grid item xs={4}>
                            <FormControl fullWidth>
                                <InputLabel id="mood-label">Mood Type</InputLabel>
                                <Select
                                    labelId="mood-label"
                                    name="moodType"
                                    required
                                    value={formData.moodType}
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
                        <Grid item xs={4}>
                            <TextField
                                label="Car Name"
                                name="car.name"
                                value={formData.car.name}
                                required
                                onChange={handleChange}
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <FormControlLabel
                                control={<Checkbox checked={formData.car.cool} onChange={handleChange}
                                                   name="car.cool"/>}
                                label="Car Cool?"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <TextField
                                label="Team ID"
                                name="teamID"
                                value={formData.teamID}
                                onChange={handleChange}
                                fullWidth
                                type="number"
                            />
                        </Grid>
                        <Grid item xs={30}>
                            <Button variant="contained" type="submit" fullWidth>
                                Create
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Box>
        </Container>
    );
}

export default CreateHumanBeingForm;
