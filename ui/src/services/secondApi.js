import axios from "axios";

const secondApiClient = axios.create({
    baseURL: 'https://localhost:15239/api/v1',
    headers: {
        "Content-Type": 'application/xml',
        Accept: 'application/xml',
    },
});

export default secondApiClient;