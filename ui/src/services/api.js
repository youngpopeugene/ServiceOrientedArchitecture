import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'https://localhost:16239/api/v1',
    headers: {
        "Content-Type": 'application/xml',
        Accept: 'application/xml',
    },
});

export default apiClient;