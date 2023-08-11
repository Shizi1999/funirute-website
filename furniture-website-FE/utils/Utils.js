export function getApiUrl(endPoint) {
    return `http://localhost:8080/api${endPoint}`;
}
export function convertToBlob(obj) {
    const json = JSON.stringify(obj);
    return new Blob([json], {
        type: 'application/json'
    });
}