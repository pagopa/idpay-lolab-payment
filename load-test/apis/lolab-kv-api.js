import http from "k6/http";

export class KVApi {
    constructor(baseUrl) {
        this.baseUrl = baseUrl;
    }

    put(key, value) {
        const response = http.put(this.baseUrl + "/" + key, value);
        return response;
    }

    get(key) {
        const response = http.get(this.baseUrl + "/" + key);
        return response;
    }
}