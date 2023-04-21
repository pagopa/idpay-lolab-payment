package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("lolab_request_transactions")
public record TransactionEntity(
    String transactionId,
    String initiativeId,
    int amount,
    String merchantId,
    String state
) {}
