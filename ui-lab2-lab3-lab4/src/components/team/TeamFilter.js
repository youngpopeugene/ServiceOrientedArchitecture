import React from 'react';
import { Alert, Container, Grid, TextField } from '@mui/material';

function TeamFilter({ filters, setFilters, sort, setSort, error }) {
    const handleFilterChange = (event) => {
        setFilters(event.target.value);
    };

    const handleSortChange = (event) => {
        setSort(event.target.value);
    };

    return (
        <Container maxWidth="sm" sx={{
            border: `2px solid ${error ? '#d32f2f' : '#1976d2'}`,
            paddingTop: 3,
            paddingBottom: 3,
            marginTop: 5,
            marginBottom: 5,
            borderRadius: 2,
        }}>
            {error && (
                <Alert severity="error" sx={{ marginBottom: 2 }}>
                    {error.message || 'An error occurred'}
                </Alert>
            )}
            <Grid container spacing={1}>
                <Grid item xs={12}>
                    <TextField
                        label="Sorting"
                        placeholder="e.g., id, -id"
                        value={sort}
                        onChange={handleSortChange}
                        fullWidth
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        label="Filters"
                        placeholder="e.g., id[eq]=1"
                        value={filters}
                        onChange={handleFilterChange}
                        fullWidth
                    />
                </Grid>
            </Grid>
        </Container>
    );
}

export default TeamFilter;
