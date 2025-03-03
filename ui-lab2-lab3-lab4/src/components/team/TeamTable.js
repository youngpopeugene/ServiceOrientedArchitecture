import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import {
    Button,
    Container,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow,
} from '@mui/material';
import EditTeamForm from './EditTeamForm';
import DeleteTeamButton from './DeleteTeamButton';

function TeamTable({ page, setPage, rowsPerPage, setRowsPerPage, error }) {
    const teams = useSelector((state) => state.teams.items);
    const total = useSelector((state) => state.teams.total);
    const [selectedTeam, setSelectedTeam] = useState(null);
    const [openEditDialog, setOpenEditDialog] = useState(false);

    const handleEditClick = (team) => {
        setSelectedTeam(team);
        setOpenEditDialog(true);
    };

    const handleCloseEditDialog = () => {
        setOpenEditDialog(false);
        setSelectedTeam(null);
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const headCells = [
        { id: 'id', label: 'ID' },
        { id: 'name', label: 'Name' },
        { id: 'actions', label: 'Actions' },
    ];

    return (
        <Container maxWidth="sm">
            <TablePagination
                component="div"
                count={total}
                page={page}
                onPageChange={handleChangePage}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleChangeRowsPerPage}
                labelRowsPerPage="Rows per page"
                labelDisplayedRows={({ from, to, count }) => `${from}-${to} of ${count}`}
                sx={{
                    border: '2px solid black',
                    borderRadius: 2,
                    display: 'flex',
                    justifyContent: 'center', // Center horizontally
                    alignItems: 'center',    // Center vertically
                }}
            />
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            {headCells.map((headCell) => (
                                <TableCell key={headCell.id} align="center">
                                    {headCell.label}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {teams.map((team) => (
                            <TableRow key={team.id}>
                                <TableCell align="center">{team.id}</TableCell>
                                <TableCell align="center">{team.name}</TableCell>
                                <TableCell align="center">
                                    <Button
                                        variant="outlined"
                                        onClick={() => handleEditClick(team)}
                                        fullWidth
                                    >
                                        Edit
                                    </Button>
                                    <DeleteTeamButton teamId={team.id} />
                                </TableCell>
                            </TableRow>
                        ))}
                        {teams.length === 0 && (
                            <TableRow>
                                <TableCell colSpan={3} align="center">
                                    No Teams Found
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            {selectedTeam && (
                <EditTeamForm
                    open={openEditDialog}
                    onClose={handleCloseEditDialog}
                    team={selectedTeam}
                />
            )}
        </Container>
    );
}

export default TeamTable;
