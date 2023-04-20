package it.gov.pagopa.idpaylolabpayment.domain;

import lombok.Getter;

@Getter
public final class Transaction {

  public static Transaction pending(TransactionId transactionId, int amount, String merchantId) {
    return new Transaction(transactionId, amount, merchantId, TransactionState.PENDING);
  }

  private final TransactionId transactionId;
  private final int amount;
  private final String merchantId;
  private TransactionState transactionState;

  public Transaction(TransactionId transactionId, int amount, String merchantId,
      TransactionState transactionState) {
    this.transactionId = transactionId;
    this.amount = amount;
    this.merchantId = merchantId;
    this.transactionState = transactionState;
  }
}
