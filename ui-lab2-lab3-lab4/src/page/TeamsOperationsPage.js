import React, { useState } from 'react';
import { Container, Typography, Box, TextField, Button, Alert, CircularProgress } from '@mui/material';
import { addHumanBeingToTeam, removeHumanBeingFromTeam, updateTeamCars, makeTeamDepressive } from '../services/secondService';

function TeamsOperationsPage() {
    const [teamId, setTeamId] = useState('');
    const [teamId2, setTeamId2] = useState('');
    const [teamId3, setTeamId3] = useState('');
    const [humanBeingId, setHumanBeingId] = useState('');
    const [error, setError] = useState(null);
    const [message, setMessage] = useState(null);

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

    const handleAddHumanBeing = async () => {
        if (!teamId.trim() || !humanBeingId.trim()) {
            setError('Please enter both Team ID and HumanBeing ID.');
            return;
        }
        setError(null);
        setMessage(null);
        try {
            await addHumanBeingToTeam(teamId.trim(), humanBeingId.trim());
            setMessage(`HumanBeing ${humanBeingId} added to Team ${teamId} successfully.`);
        } catch (err) {
            console.log(err)
            const xmlMessage = err.response.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            setError(parsedError);
        }
    };

    const handleRemoveHumanBeing = async () => {
        if (!teamId.trim() || !humanBeingId.trim()) {
            setError('Please enter both Team ID and HumanBeing ID.');
            return;
        }
        setError(null);
        setMessage(null);
        try {
            await removeHumanBeingFromTeam(teamId.trim(), humanBeingId.trim());
            setMessage(`HumanBeing ${humanBeingId} removed from Team ${teamId} successfully.`);
        } catch (err) {
            const xmlMessage = err.response.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            setError(parsedError);
        }
    };

    const handleUpdateTeamCars = async () => {
        if (!teamId2.trim()) {
            setError('Please enter a Team ID.');
            return;
        }
        setError(null);
        setMessage(null);
        try {
            await updateTeamCars(teamId2.trim());
            setMessage(`Team ${teamId2}'s cars updated successfully.`);
        } catch (err) {
            const xmlMessage = err.response.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            setError(parsedError);
        }
    };

    const handleMakeTeamDepressive = async () => {
        if (!teamId3.trim()) {
            setError('Please enter a Team ID.');
            return;
        }
        setError(null);
        setMessage(null);
        try {
            await makeTeamDepressive(teamId3.trim());
            setMessage(`Team ${teamId3} is now depressive.`);
        } catch (err) {
            const xmlMessage = err.response.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            setError(parsedError);
        }
    };

    return (
        <Container maxWidth="md" sx={{ mt: 8 }}>
            <Typography variant="h4" gutterBottom>
                Team Operations
            </Typography>
            {error && (
                <Alert severity="error" sx={{ mb: 2 }}>
                    {error}
                </Alert>
            )}
            {message && (
                <Alert severity="success" sx={{ mb: 2 }}>
                    {message}
                </Alert>
            )}
            <Box sx={{ mb: 4 }}>
                <Typography variant="h6" gutterBottom>
                    Add or Remove a HumanBeing from a Team
                </Typography>
                <TextField
                    label="Team ID"
                    value={teamId}
                    onChange={(e) => setTeamId(e.target.value)}
                    variant="outlined"
                    size="small"
                    sx={{ mr: 2, mb: 2 }}
                />
                <TextField
                    label="HumanBeing ID"
                    value={humanBeingId}
                    onChange={(e) => setHumanBeingId(e.target.value)}
                    variant="outlined"
                    size="small"
                    sx={{ mr: 2, mb: 2 }}
                />
                <Box sx={{ display: 'flex', gap: 2, mb: 2 }}>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleAddHumanBeing}
                    >
                        Add HumanBeing
                    </Button>
                    <Button
                        variant="contained"
                        color="error"
                        onClick={handleRemoveHumanBeing}
                    >
                    Remove HumanBeing
                    </Button>
                </Box>
            </Box>

            <Box sx={{ mb: 4 }}>
                <Typography variant="h6" gutterBottom>
                    Update Team Cars
                </Typography>
                <TextField
                    label="Team ID"
                    value={teamId2}
                    onChange={(e) => setTeamId2(e.target.value)}
                    variant="outlined"
                    size="small"
                    sx={{ mr: 2, mb: 2 }}
                />
                <Button
                    variant="contained"
                    color="secondary"
                    onClick={handleUpdateTeamCars}
                >
                    Update Cars
                </Button>
            </Box>

            <Box sx={{ mb: 4 }}>
                <Typography variant="h6" gutterBottom>
                    Make Team Depressive
                </Typography>
                <TextField
                    label="Team ID"
                    value={teamId3}
                    onChange={(e) => setTeamId3(e.target.value)}
                    variant="outlined"
                    size="small"
                    sx={{ mr: 2, mb: 2 }}
                />
                <Button
                    variant="contained"
                    color="warning"
                    onClick={handleMakeTeamDepressive}
                >
                    Make Depressive
                </Button>
            </Box>
        </Container>
    );
}

export default TeamsOperationsPage;
