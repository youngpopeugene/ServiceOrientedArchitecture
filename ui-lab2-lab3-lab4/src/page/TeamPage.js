import React, {useEffect, useState} from 'react';
import {useDispatch} from 'react-redux';
import CreateTeamForm from '../components/team/CreateTeamForm';
import TeamFilter from '../components/team/TeamFilter';
import TeamTable from '../components/team/TeamTable';
import {loadTeams} from '../store/teamsSlice';

function TeamPage() {
    const dispatch = useDispatch();
    const [filter, setFilter] = useState('');
    const [sort, setSort] = useState('');
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [error, setError] = useState(null);

    const fetchTeamsWithFilters = async () => {
        try {
            await dispatch(
                loadTeams({
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
        fetchTeamsWithFilters();
    }, [filter, sort, page, rowsPerPage]);

    return (
        <>
            <CreateTeamForm/>
            <TeamFilter
                filters={filter}
                setFilters={setFilter}
                sort={sort}
                setSort={setSort}
                error={error}
            />
            <TeamTable
                page={page}
                setPage={setPage}
                rowsPerPage={rowsPerPage}
                setRowsPerPage={setRowsPerPage}
            />
        </>
    );
}

export default TeamPage;
