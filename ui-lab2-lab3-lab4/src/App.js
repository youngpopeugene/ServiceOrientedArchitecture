import React from 'react';
import Header from "./components/Header";
import {Route, Routes} from "react-router-dom";
import HumanBeingPage from "./page/HumanBeingPage";
import TeamPage from "./page/TeamPage";
import HelloPage from "./page/HelloPage";
import CalculationsPage from "./page/CalculationsPage";
import TeamsOperationsPage from "./page/TeamsOperationsPage";

function App() {
    return (
        <>
            <Header/>
            <Routes>
                <Route path="/operations" element={<TeamsOperationsPage/>}/>
                <Route path="/calculations" element={<CalculationsPage/>}/>
                <Route path="/humanbeings" element={<HumanBeingPage/>}/>
                <Route path="/teams" element={<TeamPage/>}/>
                <Route path="/" element={<HelloPage/>}/>
            </Routes>
        </>
    );
}

export default App;
