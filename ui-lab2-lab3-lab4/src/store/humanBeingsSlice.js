import {createAsyncThunk, createSlice} from '@reduxjs/toolkit';
import {
    createHumanBeing,
    deleteHumanBeing,
    fetchHumanBeings,
    updateHumanBeing,
    fetchHumanBeingsByImpactSpeed,
} from '../services/humanBeingService';

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

export const loadHumanBeings = createAsyncThunk(
    'humanbeings/loadHumanBeings',
    async (params, {rejectWithValue}) => {
        try {
            return await fetchHumanBeings(params);
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

export const addHumanBeing = createAsyncThunk(
    'humanbeings/addHumanBeing',
    async (data, {rejectWithValue}) => {
        try {
            return await createHumanBeing(data);
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

export const editHumanBeing = createAsyncThunk(
    'humanbeings/editHumanBeing',
    async ({id, updatedHB}, {rejectWithValue}) => {
        try {
            return await updateHumanBeing(id, updatedHB);
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

export const loadHumanBeingsByImpactSpeed = createAsyncThunk(
    'humanbeings/loadHumanBeingsByImpactSpeed',
    async (impactSpeed, {rejectWithValue}) => {
        try {
            return await fetchHumanBeingsByImpactSpeed(impactSpeed);
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

export const removeHumanBeing = createAsyncThunk(
    'humanbeings/removeHumanBeing',
    async (id, {rejectWithValue}) => {
        try {
            await deleteHumanBeing(id);
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

const humanBeingsSlice = createSlice({
    name: 'humanbeings',
    initialState: {
        items: [],
        items2: [],
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
            state.pagination = {...state.pagination, ...action.payload};
        },
        setSorting(state, action) {
            state.filters.sortField = action.payload.sortField;
            state.filters.sortOrder = action.payload.sortOrder;
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(loadHumanBeingsByImpactSpeed.fulfilled, (state, action) => {
                state.items2 = action.payload.humanBeings2;
            })
            .addCase(loadHumanBeings.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(loadHumanBeings.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.items = action.payload.humanBeings;
                state.total = action.payload.total;
            })
            .addCase(loadHumanBeings.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload?.message || 'Error loading human beings';
            })
            .addCase(addHumanBeing.fulfilled, (state, action) => {
                state.items.push(action.payload);
                state.total += 1;
            })
            .addCase(addHumanBeing.rejected, (state, action) => {
                state.error = action.payload?.message || 'Error adding human being';
            })
            .addCase(editHumanBeing.fulfilled, (state, action) => {
                const index = state.items.findIndex((hb) => hb.id === action.payload.id);
                if (index !== -1) {
                    state.items[index] = action.payload;
                }
                state.error = null;
            })
            .addCase(editHumanBeing.rejected, (state, action) => {
                state.error = action.payload?.message || 'Error updating human being';
            })
            .addCase(removeHumanBeing.fulfilled, (state, action) => {
                state.items = state.items.filter((hb) => hb.id !== action.payload);
                state.total -= 1;
                state.error = null;
            })
            .addCase(removeHumanBeing.rejected, (state, action) => {
                state.error = action.payload?.message || 'Error deleting human being';
            });
    },
});

export const {setFilters, setPagination, setSorting} = humanBeingsSlice.actions;
export default humanBeingsSlice.reducer;
