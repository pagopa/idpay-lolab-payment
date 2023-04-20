package it.gov.pagopa.idpaylolabpayment.domain;

import reactor.core.publisher.Mono;

public interface TransactionRepository {
  Mono<Transaction> findByTransactionId(TransactionId transactionId);
  Mono<Transaction> save(Transaction requestTransaction);
}
