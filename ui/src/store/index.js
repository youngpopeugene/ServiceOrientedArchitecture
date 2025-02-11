import { configureStore } from '@reduxjs/toolkit';
import humanBeingsReducer from './humanBeingsSlice';
import teamsReducer from './teamsSlice';

const store = configureStore({
    reducer: {
        humanbeings: humanBeingsReducer,
        teams: teamsReducer,
    },
});

export default store;
