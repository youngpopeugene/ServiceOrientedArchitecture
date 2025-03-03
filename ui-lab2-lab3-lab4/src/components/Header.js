import React from 'react';
import {AppBar, Button, Toolbar} from '@mui/material';
import {useNavigate} from 'react-router-dom';

function Header() {
    const navigate = useNavigate();

    const handleHumanBeings = () => {
        navigate('/humanbeings');
    };

    const handleTeams = () => {
        navigate('/teams');
    };

    const handleMain = () => {
        navigate('/');
    };

    const handleCalculations = () => {
        navigate('/calculations');
    };

    const handleOperations = () => {
        navigate('/operations');
    };

    return (
        <AppBar position="static">
            <Toolbar>
                <Button color="inherit" variant="outlined" onClick={handleMain} sx={{ mr: 1, ml: 1 }}>
                    Main Page
                </Button>
                <Button color="inherit" variant="outlined" onClick={handleHumanBeings} sx={{ mr: 1, ml: 1 }}>
                    HumanBeings
                </Button>
                <Button color="inherit" variant="outlined" onClick={handleTeams} sx={{ mr: 1, ml: 1 }}>
                    Teams
                </Button>
                <Button color="inherit" variant="outlined" onClick={handleCalculations} sx={{ mr: 1, ml: 1 }}>
                    Calculations
                </Button>
                <Button color="inherit" variant="outlined" onClick={handleOperations} sx={{ mr: 1, ml: 1 }}>
                    Team Operations
                </Button>
            </Toolbar>
        </AppBar>
    );
}

export default Header;
