import http from "k6/http";

export class TransactionApi {

    constructor(baseUrl) {
        this.baseUrl = baseUrl;
        this.count = 0;
    }
    createTransaction(amount, merchantId) {
        const response = http.post(this.baseUrl, JSON.stringify({
            transaction: {
                amount: amount
            },
            merchantId: merchantId
        }), {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response;
    }

    get(transactionId) {
        const response = http.get(this.baseUrl + "/" + transactionId);
        return response;
    }
}