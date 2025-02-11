import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import store from './store';
import App from './App';
import CssBaseline from '@mui/material/CssBaseline';
import {HashRouter} from "react-router-dom";

ReactDOM.render(
    <Provider store={store}>
        <HashRouter>
            <CssBaseline />
            <App />
        </HashRouter>
    </Provider>,
    document.getElementById('root')
);
