import React, {useState} from 'react';
import {
    Alert,
    Box,
    Button,
    Container,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField,
    Typography
} from '@mui/material';
import {getHumanBeingsCountBySoundtrackNameLessThan, getImpactSpeedSum} from '../services/humanBeingService';
import {loadHumanBeingsByImpactSpeed} from "../store/humanBeingsSlice";
import {useDispatch, useSelector} from "react-redux";

function CalculationsPage() {
    const dispatch = useDispatch();
    const humanBeings2 = useSelector((state) => state.humanbeings.items2);
    const [impactSpeed, setImpactSpeed] = useState('');
    const [impactSpeedSum, setImpactSpeedSum] = useState(null);
    const [soundtrackName, setSoundtrackName] = useState('');
    const [soundtrackCount, setSoundtrackCount] = useState(null);
    const [error, setError] = useState(null);


    const parseXMLToJSON = (xmlString) => {
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlString, 'application/xml');
        const fields = xmlDoc.getElementsByTagName('field');
        let resultString = '';
        if (fields.length !== 0) resultString = ': ';
        for (let i = 0; i < fields.length; i++) {
            if (i !== 0) resultString += ', '
            resultString += fields[i].textContent;
        }
        return xmlDoc.getElementsByTagName('message')[0]?.textContent + resultString || 'Unknown error';
    };

    const handleGetImpactSpeedSum = async () => {
        try {
            setError(null);
            const sum = await getImpactSpeedSum();
            setImpactSpeedSum(sum);
        } catch (err) {
            console.log(err)
            const xmlMessage = err.response.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            setError(parsedError);
        }
    };

    const handleGetCountBySoundtrackName = async () => {
        if (!soundtrackName.trim()) {
            setError('Please enter a soundtrack name.');
            return;
        }
        try {
            setError(null);
            const count = await getHumanBeingsCountBySoundtrackNameLessThan(soundtrackName.trim());
            setSoundtrackCount(count);
        } catch (err) {
            const xmlMessage = err.response.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            setError(parsedError);
        }
    };

    const handleFilterByImpactSpeed = async () => {
        if (!impactSpeed.trim() || isNaN(impactSpeed)) {
            setError('Please enter a valid impact speed.');
            return;
        }
        try {
            setError(null);
            await dispatch(
                loadHumanBeingsByImpactSpeed(parseFloat(impactSpeed))
            ).unwrap();
        } catch (err) {
            const xmlMessage = err.response.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            setError(parsedError);
        }
    };

    const headCells = [
        {id: 'id', label: 'ID'},
        {id: 'name', label: 'Name'},
        {id: 'coordinates.x', label: 'X'},
        {id: 'coordinates.y', label: 'Y'},
        {id: 'creationDate', label: 'Creation Date'},
        {id: 'realHero', label: 'Real Hero'},
        {id: 'hasToothpick', label: 'Has Toothpick'},
        {id: 'impactSpeed', label: 'Impact Speed'},
        {id: 'soundtrackName', label: 'Soundtrack Name'},
        {id: 'minutesOfWaiting', label: 'Minutes Of Waiting'},
        {id: 'weaponType', label: 'Weapon Type'},
        {id: 'moodType', label: 'Mood Type'},
        {id: 'car.name', label: 'Car Name'},
        {id: 'car.cool', label: 'Car Cool'},
        {id: 'teamID', label: 'Team ID'},
    ];

    return (
        <Container maxWidth="md" sx={{mt: 8}}>
            <Typography variant="h4" gutterBottom>
                Calculations
            </Typography>
            {error && (
                <Alert severity="error" sx={{mb: 2}}>
                    {error}
                </Alert>
            )}

            <Box sx={{mb: 4}}>
                <Typography variant="h6" gutterBottom>
                    Impact Speed Sum
                </Typography>
                <Typography variant="body1" sx={{mb: 2}}>
                    Click the button to fetch the sum of all Impact Speeds.
                </Typography>
                <Button variant="contained" color="primary" onClick={handleGetImpactSpeedSum}>
                    Get Impact Speed Sum
                </Button>
                {impactSpeedSum !== null && (
                    <Typography variant="body1" sx={{mt: 2}}>
                        Impact Speed Sum: <strong>{impactSpeedSum}</strong>
                    </Typography>
                )}
            </Box>

            <Box sx={{mb: 4}}>
                <Typography variant="h6" gutterBottom>
                    HumanBeings Count by SoundtrackName
                </Typography>
                <Typography variant="body1" sx={{mb: 2}}>
                    Enter a soundtrack name and get the count of HumanBeings whose soundtrack name is lexicographically
                    less than the given value.
                </Typography>
                <Box sx={{display: 'flex', alignItems: 'center', gap: 2, mb: 2}}>
                    <TextField
                        label="Soundtrack Name"
                        value={soundtrackName}
                        onChange={(e) => setSoundtrackName(e.target.value)}
                        variant="outlined"
                        size="small"
                    />
                    <Button variant="contained" color="secondary" onClick={handleGetCountBySoundtrackName}>
                        Get Count
                    </Button>
                </Box>
                {soundtrackCount !== null && (
                    <Typography variant="body1" sx={{mt: 2}}>
                        Count of HumanBeings: <strong>{soundtrackCount}</strong>
                    </Typography>
                )}
            </Box>
            <Box sx={{mt: 4, mb: 4}}>
                <Typography variant="h6" gutterBottom>
                    Filter by Impact Speed
                </Typography>
                <Box sx={{display: 'flex', alignItems: 'center', gap: 2, mb: 2}}>
                    <TextField
                        label="Impact Speed"
                        value={impactSpeed}
                        onChange={(e) => setImpactSpeed(e.target.value)}
                        variant="outlined"
                        size="small"
                    />
                    <Button variant="contained" color="primary" onClick={handleFilterByImpactSpeed}>
                        Filter Table
                    </Button>
                </Box>
            </Box>
            <TableContainer component={Paper} style={{marginTop: '40px'}}>
                <Table>
                    <TableHead>
                        <TableRow>
                            {headCells.map((headCell) => (
                                <TableCell key={headCell.id} align={"center"}>
                                    {headCell.label}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {humanBeings2.map((hb) => (
                            <TableRow key={hb.id}>
                                <TableCell align={"center"}>{hb.id}</TableCell>
                                <TableCell align={"center"}>{hb.name}</TableCell>
                                <TableCell align={"center"}>{hb.coordinates.x}</TableCell>
                                <TableCell align={"center"}>{hb.coordinates.y}</TableCell>
                                <TableCell align={"center"}>{hb.creationDate}</TableCell>
                                <TableCell align={"center"}>{hb.realHero ? 'Yes' : 'No'}</TableCell>
                                <TableCell align={"center"}>{hb.hasToothpick ? 'Yes' : 'No'}</TableCell>
                                <TableCell align={"center"}>{hb.impactSpeed}</TableCell>
                                <TableCell align={"center"}>{hb.soundtrackName}</TableCell>
                                <TableCell align={"center"}>{hb.minutesOfWaiting}</TableCell>
                                <TableCell align={"center"}>{hb.weaponType}</TableCell>
                                <TableCell align={"center"}>{hb.moodType}</TableCell>
                                <TableCell align={"center"}>{hb.car.name}</TableCell>
                                <TableCell align={"center"}>{hb.car.cool ? 'Yes' : 'No'}</TableCell>
                                <TableCell align={"center"}>{hb.teamID || ''}</TableCell>
                            </TableRow>
                        ))}
                        {humanBeings2.length === 0 && (
                            <TableRow>
                                <TableCell colSpan={15}>
                                    No HumanBeings Found
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
)
    ;
}

export default CalculationsPage;
