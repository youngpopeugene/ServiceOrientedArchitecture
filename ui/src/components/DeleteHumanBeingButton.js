import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { removeHumanBeing } from '../store/humanBeingsSlice';
import { Button, Dialog, DialogTitle, DialogActions } from '@mui/material';

function DeleteHumanBeingButton({ hbId }) {
    const dispatch = useDispatch();
    const [openConfirm, setOpenConfirm] = useState(false);

    const handleDeleteClick = () => {
        setOpenConfirm(true);
    };

    const handleConfirm = async () => {
        try {
            await dispatch(removeHumanBeing(hbId)).unwrap();
            alert('HumanBeing deleted successfully!');
        } catch (err) {
            alert(err.message());
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
                <DialogTitle>Are you sure you want to delete this HumanBeing?</DialogTitle>
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

export default DeleteHumanBeingButton;
