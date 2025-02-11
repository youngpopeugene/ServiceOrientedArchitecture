import React, {useEffect, useState} from 'react';
import {useDispatch} from 'react-redux';
import CreateHumanBeingForm from '../components/CreateHumanBeingForm';
import HumanBeingFilter from '../components/HumanBeingFilter';
import HumanBeingTable from '../components/HumanBeingTable';
import {loadHumanBeings} from '../store/humanBeingsSlice';
import {fetchEnums} from '../services/humanBeingService';

function HumanBeingPage() {
    const dispatch = useDispatch();
    const [filter, setFilter] = useState('');
    const [sort, setSort] = useState('');
    const [enums, setEnums] = useState({
        weaponTypes: [],
        moodTypes: [],
    });
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [error, setError] = useState(null);


    const fetchHumanBeingsWithFilters = async () => {
        try {
            await dispatch(
                loadHumanBeings({
                    filterValues: filter,
                    sortValues: sort,
                    limit: rowsPerPage,
                    offset: page * rowsPerPage,
                })
            ).unwrap();
            setError(null);
        } catch (err) {
            console.log(err)
            if (err.name !== "TypeError") setError(err);
        }
    };

    useEffect(() => {
        fetchEnums()
            .then((data) => {
                setEnums(data);
            })
            .catch((err) => console.error('Error fetching enums:', err));
    }, []);

    useEffect(() => {
        fetchHumanBeingsWithFilters();
    }, [filter, sort, page, rowsPerPage]);

    return (
        <>
            <CreateHumanBeingForm/>
            <HumanBeingFilter
                filters={filter}
                setFilters={setFilter}
                sort={sort}
                setSort={setSort}
                error={error}
            />
            <HumanBeingTable
                page={page}
                setPage={setPage}
                rowsPerPage={rowsPerPage}
                setRowsPerPage={setRowsPerPage}
            />
        </>
    );
}

export default HumanBeingPage;
