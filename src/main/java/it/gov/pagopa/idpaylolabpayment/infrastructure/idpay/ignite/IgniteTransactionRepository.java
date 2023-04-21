package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.ignite;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionId;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionRepository;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.transactions.TransactionException;
import reactor.core.publisher.Mono;

public class IgniteTransactionRepository implements TransactionRepository {

  private final IgniteCache<String, Transaction> cache;

  public IgniteTransactionRepository(IgniteCache<String, Transaction> cache) {
    this.cache = cache;
  }

  @Override
  public Mono<Transaction> findByTransactionId(TransactionId transactionId) {
    return Mono.fromCallable(
        () -> cache.get(transactionId.value())
    );
  }

  @Override
  public Mono<Transaction> save(Transaction requestTransaction) {
    return Mono.create(monoSink -> {
      try {
        cache.put(requestTransaction.getTransactionId().value(), requestTransaction);
        monoSink.success(requestTransaction);
      } catch (TransactionException e) {
        monoSink.error(e);
      }
    });
  }

}

