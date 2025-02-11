import apiClient from './api';

export const fetchTeams = (params) => {
    const searchParams = new URLSearchParams();

    if (params.limit) searchParams.append('limit', params.limit);
    if (params.offset) searchParams.append('offset', params.offset);

    if (params.sortValues && typeof params.sortValues === 'string') {
        const sortArray = params.sortValues.split(",").map(sort => sort.trim()).filter(sort => sort !== '');
        sortArray.forEach(sort => {
            searchParams.append('sort', sort);
        });
    }

    if (params.filterValues && typeof params.filterValues === 'string') {
        const filterArray = params.filterValues.split(",").map(filter => filter.trim()).filter(filter => filter !== '');
        filterArray.forEach(filter => {
            searchParams.append('filter', filter);
        });
    }

    return apiClient.get(`teams?${searchParams.toString()}`)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            const totalItems = parseInt(xmlDoc.getElementsByTagName('totalItems')[0]?.textContent || '0', 10);
            const totalPages = parseInt(xmlDoc.getElementsByTagName('totalPages')[0]?.textContent || '0', 10);
            const currentPage = parseInt(xmlDoc.getElementsByTagName('currentPage')[0]?.textContent || '0', 10);
            const hbNodes = xmlDoc.getElementsByTagName('teams')[0]?.getElementsByTagName('team') || [];
            const teams = Array.from(hbNodes).map((node) => parseTeamXML(node));
            return { teams, total: totalItems };
        })
        .catch((error) => {
            throw error.response;
        });
};

export const createTeam = (newHB) => {
    const xmlData = `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>${jsonToXml(newHB, 'team', { enums: ['weaponType', 'moodType'] })}`;
    return apiClient.post('teams', xmlData)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            return parseTeamXML(xmlDoc.getElementsByTagName('team')[0]);
        })
        .catch((error) => {
            throw error.response;
        });
};

export const getTeamById = (id) => {
    return apiClient.get(`teams/${id}`)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            const node = xmlDoc.getElementsByTagName('team')[0];
            return parseTeamXML(node);
        })
        .catch((error) => {
            throw error.response;
        });
};

export const updateTeam = (id, updatedHB) => {
    const xmlData = `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>${jsonToXml(updatedHB, 'team', { enums: ['weaponType', 'moodType'] })}`;
    return apiClient.put(`teams/${id}`, xmlData)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            const node = xmlDoc.getElementsByTagName('team')[0];
            return parseTeamXML(node);
        })
        .catch((error) => {
            throw error.response;
        });
};

export const deleteTeam = (id) => {
    return apiClient.delete(`teams/${id}`)
        .then(() => ({ id }))
        .catch((error) => {
            throw error.response;
        });
};

function parseTeamXML(node) {
    const getText = (tag) => node.getElementsByTagName(tag)[0]?.textContent || '';
    return {
        id: parseInt(getText('id'), 10),
        name: getText('name'),
    };
}

function jsonToXml(obj, rootName, options = {}) {
    let xml = rootName ? `<${rootName}>` : '';
    for (let prop in obj) {
        if (obj.hasOwnProperty(prop)) {
            const value = obj[prop];
            const tagName = prop.charAt(0).toLowerCase() + prop.slice(1);
            if (value === null || value === undefined || value === '') {
                continue;
            }
            if (typeof value === 'object' && !Array.isArray(value)) {
                xml += jsonToXml(value, tagName, options);
            } else {
                if (options.enums && options.enums.includes(prop)) {
                    xml += `<${tagName}>${value.toUpperCase()}</${tagName}>`;
                } else {
                    xml += `<${tagName}>${value}</${tagName}>`;
                }
            }
        }
    }
    xml += rootName ? `</${rootName}>` : '';
    return xml;
}
