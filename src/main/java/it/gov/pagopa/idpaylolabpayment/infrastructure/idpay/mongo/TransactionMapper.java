package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionId;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionState;

public class TransactionMapper {

  public Transaction fromEntity(TransactionEntity transaction) {
    return new Transaction(
        new TransactionId(transaction.transactionId()),
        transaction.amount(),
        transaction.merchantId(),
        TransactionState.valueOf(transaction.state())
    );
  }

  public TransactionEntity toEntity(Transaction transaction) {
    return new TransactionEntity(
        transaction.getTransactionId().value(),
        "",
        transaction.getAmount(),
        transaction.getMerchantId(),
        transaction.getTransactionState().name()
    );
  }
}
