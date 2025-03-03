import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { createTeam, deleteTeam, fetchTeams, updateTeam } from '../services/teamService';

const parseXMLToJSON = (xmlString) => {
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(xmlString, 'application/xml');
    const fields = xmlDoc.getElementsByTagName('field');
    let resultString = '';
    if (fields.length !== 0) resultString = ': ';
    for (let i = 0; i < fields.length; i++) {
        if (i !== 0) resultString += ', ';
        resultString += fields[i].textContent;
    }
    return xmlDoc.getElementsByTagName('message')[0]?.textContent + resultString || 'Unknown error';
};

export const loadTeams = createAsyncThunk(
    'teams/loadTeams',
    async (params, { rejectWithValue }) => {
        try {
            return await fetchTeams(params);
        } catch (error) {
            const xmlMessage = error.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            return rejectWithValue({
                status: error.status,
                message: parsedError,
            });
        }
    }
);

export const addTeam = createAsyncThunk(
    'teams/addTeam',
    async (data, { rejectWithValue }) => {
        try {
            return await createTeam(data);
        } catch (error) {
            const xmlMessage = error.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            return rejectWithValue({
                status: error.status,
                message: parsedError,
            });
        }
    }
);

export const editTeam = createAsyncThunk(
    'teams/editTeam',
    async ({ id, updatedTeam }, { rejectWithValue }) => {
        try {
            return await updateTeam(id, updatedTeam);
        } catch (error) {
            const xmlMessage = error.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            return rejectWithValue({
                status: error.status,
                message: parsedError,
            });
        }
    }
);

export const removeTeam = createAsyncThunk(
    'teams/removeTeam',
    async (id, { rejectWithValue }) => {
        try {
            await deleteTeam(id);
            return id;
        } catch (error) {
            const xmlMessage = error.data;
            const parsedError = parseXMLToJSON(xmlMessage);
            return rejectWithValue({
                status: error.status,
                message: parsedError,
            });
        }
    }
);

const teamsSlice = createSlice({
    name: 'teams',
    initialState: {
        items: [],
        total: 0,
        status: 'idle',
        error: null,
        filters: {
            filterValues: '',
            sortValues: '',
        },
        pagination: {
            limit: 10,
            offset: 0,
        },
    },
    reducers: {
        setFilters(state, action) {
            state.filters = {
                ...state.filters,
                ...action.payload,
            };
            state.pagination.offset = 0;
        },
        setPagination(state, action) {
            state.pagination = { ...state.pagination, ...action.payload };
        },
        setSorting(state, action) {
            state.filters.sortField = action.payload.sortField;
            state.filters.sortOrder = action.payload.sortOrder;
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(loadTeams.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(loadTeams.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.items = action.payload.teams;
                state.total = action.payload.total;
            })
            .addCase(loadTeams.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload?.message || 'Error loading teams';
            })
            .addCase(addTeam.fulfilled, (state, action) => {
                state.items.push(action.payload);
                state.total += 1;
            })
            .addCase(addTeam.rejected, (state, action) => {
                state.error = action.payload?.message || 'Error adding team';
            })
            .addCase(editTeam.fulfilled, (state, action) => {
                const index = state.items.findIndex((team) => team.id === action.payload.id);
                if (index !== -1) {
                    state.items[index] = action.payload;
                }
                state.error = null;
            })
            .addCase(editTeam.rejected, (state, action) => {
                state.error = action.payload?.message || 'Error updating team';
            })
            .addCase(removeTeam.fulfilled, (state, action) => {
                state.items = state.items.filter((team) => team.id !== action.payload);
                state.total -= 1;
                state.error = null;
            })
            .addCase(removeTeam.rejected, (state, action) => {
                state.error = action.payload?.message || 'Error deleting team';
            });
    },
});

export const { setFilters, setPagination, setSorting } = teamsSlice.actions;
export default teamsSlice.reducer;
