import secondApiClient from './secondApi';

export const addHumanBeingToTeam = (teamId, humanBeingId) => {
    return secondApiClient.patch(`teams/${teamId}/join/${humanBeingId}`)
        .then((response) => {})
        .catch((error) => {
            console.log(error)
            throw error;
        });
};

export const removeHumanBeingFromTeam = (teamId, humanBeingId) => {
    return secondApiClient.delete(`teams/${teamId}/unjoin/${humanBeingId}`)
        .then((response) => {})
        .catch((error) => {
            throw error;
        });
};

export const updateTeamCars = (teamId) => {
    return secondApiClient.patch(`teams/${teamId}/car/add`)
        .then((response) => {})
        .catch((error) => {
            throw error;
        });
};

export const makeTeamDepressive = (teamId) => {
    return secondApiClient.patch(`teams/${teamId}/make-depressive`)
        .then((response) => {})
        .catch((error) => {
            throw error;
        });
};
