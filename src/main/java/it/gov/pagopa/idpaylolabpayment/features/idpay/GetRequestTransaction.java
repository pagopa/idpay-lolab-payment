package it.gov.pagopa.idpaylolabpayment.features.idpay;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionId;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionRepository;
import reactor.core.publisher.Mono;

public class GetRequestTransaction {

  private final TransactionRepository transactionRepository;

  public GetRequestTransaction(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public Mono<Transaction> handle(TransactionId transactionId) {
    return transactionRepository.findByTransactionId(transactionId);
  }
}
