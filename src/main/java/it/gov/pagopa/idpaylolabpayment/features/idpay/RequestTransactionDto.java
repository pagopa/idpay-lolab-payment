package it.gov.pagopa.idpaylolabpayment.features.idpay;

public record RequestTransactionDto(
    String merchantId,
    TransactionDto transaction
) {

  record TransactionDto(
      int amount
  ) {};
}
