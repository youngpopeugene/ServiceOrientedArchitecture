import React, {useState} from 'react';
import {useSelector} from 'react-redux';
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
import EditHumanBeingForm from './EditHumanBeingForm';
import DeleteHumanBeingButton from './DeleteHumanBeingButton';

function HumanBeingTable({page, setPage, rowsPerPage, setRowsPerPage, onRequestSort}) {
    const humanBeings = useSelector((state) => state.humanbeings.items);
    const total = useSelector((state) => state.humanbeings.total);
    const [selectedHB, setSelectedHB] = useState(null);
    const [openEditDialog, setOpenEditDialog] = useState(false);

    const handleEditClick = (hb) => {
        setSelectedHB(hb);
        setOpenEditDialog(true);
    };

    const handleCloseEditDialog = () => {
        setOpenEditDialog(false);
        setSelectedHB(null);
    };

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
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
        {id: 'actions', label: 'Actions'},
    ];

    return (
        <Container maxWidth="xl">
            <TablePagination
                component="div"
                count={total}
                page={page}
                onPageChange={handleChangePage}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleChangeRowsPerPage}
                labelRowsPerPage="Rows per page"
                labelDisplayedRows={({from, to, count}) => `${from}-${to} of ${count}`}
                sx={{
                    border: '2px solid black',
                    borderRadius: 2,
                    display: 'flex',
                    justifyContent: 'center', // Center horizontally
                    alignItems: 'center',    // Center vertically
                }}
            />
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
                        {humanBeings.map((hb) => (
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
                                <TableCell>
                                    <Button variant="outlined" fullWidth onClick={() => handleEditClick(hb)}>
                                        Edit
                                    </Button>
                                    <DeleteHumanBeingButton hbId={hb.id}/>
                                </TableCell>
                            </TableRow>
                        ))}
                        {humanBeings.length === 0 && (
                            <TableRow>
                                <TableCell colSpan={16} align="center">
                                    No HumanBeings Found
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
            {selectedHB && (
                <EditHumanBeingForm
                    open={openEditDialog}
                    onClose={handleCloseEditDialog}
                    humanBeing={selectedHB}
                />
            )}
        </Container>
    );
}

export default HumanBeingTable;
