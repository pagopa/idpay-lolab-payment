package it.gov.pagopa.idpaylolabpayment.features.idpay;

import it.gov.pagopa.idpaylolabpayment.domain.TransactionIdGenerator;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionRepository;
import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionId;
import reactor.core.publisher.Mono;

public class AddRequestTransaction {

  private final TransactionRepository repository;
  private final TransactionIdGenerator idGenerator;

  public AddRequestTransaction(TransactionRepository repository, TransactionIdGenerator idGenerator) {
    this.repository = repository;
    this.idGenerator = idGenerator;
  }

  public Mono<TransactionId> handle(RequestTransactionDto requestTransactionDto) {
    return idGenerator.generateId()
        .map(transactionId -> Transaction.pending(transactionId,
            requestTransactionDto.transaction().amount(),
            requestTransactionDto.merchantId()
        ))
        .flatMap(repository::save)
        .map(Transaction::getTransactionId);
  }
}
