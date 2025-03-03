import apiClient from './api';

export const fetchHumanBeings = (params) => {
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

    return apiClient.get(`humanbeings?${searchParams.toString()}`)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            const totalItems = parseInt(xmlDoc.getElementsByTagName('totalItems')[0]?.textContent || '0', 10);
            const totalPages = parseInt(xmlDoc.getElementsByTagName('totalPages')[0]?.textContent || '0', 10);
            const currentPage = parseInt(xmlDoc.getElementsByTagName('currentPage')[0]?.textContent || '0', 10);
            const hbNodes = xmlDoc.getElementsByTagName('humanBeings')[0]?.getElementsByTagName('humanBeing') || [];
            const humanBeings = Array.from(hbNodes).map((node) => parseHumanBeingXML(node));
            return { humanBeings, total: totalItems };
        })
        .catch((error) => {
            throw error.response;
        });
};

export const fetchHumanBeingsByImpactSpeed = (impactSpeed) => {
    return apiClient.get(`humanbeings?limit=10000000&filter=impactSpeed[gt]=${impactSpeed}`)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            const hbNodes = xmlDoc.getElementsByTagName('humanBeings')[0]?.getElementsByTagName('humanBeing') || [];
            const humanBeings2 = Array.from(hbNodes).map((node) => parseHumanBeingXML(node));
            return { humanBeings2 };
        })
        .catch((error) => {
            throw error.response;
        });
};

export const createHumanBeing = (newHB) => {
    const xmlData = `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>${jsonToXml(newHB, 'humanBeing', { enums: ['weaponType', 'moodType'] })}`;
    return apiClient.post('humanbeings', xmlData)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            return parseHumanBeingXML(xmlDoc.getElementsByTagName('humanBeing')[0]);
        })
        .catch((error) => {
            throw error.response;
        });
};

export const getHumanBeingById = (id) => {
    return apiClient.get(`humanbeings/${id}`)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            const node = xmlDoc.getElementsByTagName('humanBeing')[0];
            return parseHumanBeingXML(node);
        })
        .catch((error) => {
            throw error.response;
        });
};

export const updateHumanBeing = (id, updatedHB) => {
    const xmlData = `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>${jsonToXml(updatedHB, 'humanBeing', { enums: ['weaponType', 'moodType'] })}`;
    return apiClient.put(`humanbeings/${id}`, xmlData)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            const node = xmlDoc.getElementsByTagName('humanBeing')[0];
            return parseHumanBeingXML(node);
        })
        .catch((error) => {
            throw error.response;
        });
};

export const deleteHumanBeing = (id) => {
    return apiClient.delete(`humanbeings/${id}`)
        .then(() => ({ id }))
        .catch((error) => {
            throw error.response;
        });
};

export const fetchEnums = () => {
    return Promise.all([
        apiClient.get('/weapons'),
        apiClient.get('/moods'),
    ]).then(([weaponResp, moodResp]) => {
        const parser = new DOMParser();
        const parseEnumResponse = (data, rootTag, itemTag) => {
            const xmlDoc = parser.parseFromString(data, 'application/xml');
            const elements = xmlDoc.getElementsByTagName(itemTag);
            return Array.from(elements).map((el) => el.textContent);
        };
        const weapons = parseEnumResponse(weaponResp.data, 'weapons', 'weapon');
        const moods = parseEnumResponse(moodResp.data, 'mooods', 'mood');
        return { weaponTypes: weapons, moodTypes: moods };
    }).catch((error) => {
        const xmlMessage = error.response.data;
        const parsedError = parseXMLToJSON(xmlMessage);
        throw {
            status: error.status,
            message: parsedError,
        };
    });
};

export const getImpactSpeedSum = () => {
    return apiClient.get('humanbeings/sum/impact')
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            return parseFloat(xmlDoc.getElementsByTagName('result')[0]?.textContent || '0');
        })
        .catch((error) => {
            throw error;
        });
};

export const getHumanBeingsCountBySoundtrackNameLessThan = (soundtrackName) => {
    return apiClient.get(`humanbeings/amount/soundtrack/less?soundtrackName=${encodeURIComponent(soundtrackName)}`)
        .then((response) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(response.data, 'application/xml');
            return parseInt(xmlDoc.getElementsByTagName('result')[0]?.textContent || '0', 10);
        })
        .catch((error) => {
            throw error;
        });
};

function parseHumanBeingXML(node) {
    const getText = (tag) => node.getElementsByTagName(tag)[0]?.textContent || '';
    const coordinatesNode = node.getElementsByTagName('coordinates')[0];
    const carNode = node.getElementsByTagName('car')[0];

    return {
        id: parseInt(getText('id'), 10),
        name: getText('name'),
        coordinates: {
            x: parseInt(coordinatesNode?.getElementsByTagName('x')[0]?.textContent || '0', 10),
            y: parseInt(coordinatesNode?.getElementsByTagName('y')[0]?.textContent || '0', 10),
        },
        creationDate: getText('creationDate'),
        realHero: getText('realHero') === 'true',
        hasToothpick: getText('hasToothpick') === 'true',
        impactSpeed: parseFloat(getText('impactSpeed')),
        soundtrackName: getText('soundtrackName'),
        minutesOfWaiting: parseFloat(getText('minutesOfWaiting')),
        weaponType: getText('weaponType'),
        moodType: getText('moodType'),
        car: {
            name: carNode?.getElementsByTagName('name')[0]?.textContent || '',
            cool: carNode?.getElementsByTagName('cool')[0]?.textContent === 'true'
        },
        teamID: getText('teamID') ? parseInt(getText('teamID'), 10) : null,
    };
}

// Convert JSON to XML (similar logic as in your previous code)
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
