package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.ignite;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionId;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionRepository;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo.TransactionEntity;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo.TransactionMapper;
import org.apache.ignite.IgniteCache;
import reactor.core.publisher.Mono;

public class IgniteTransactionRepository implements TransactionRepository {

  private final IgniteCache<String, TransactionEntity> cache;
  private final TransactionMapper mapper;

  public IgniteTransactionRepository(IgniteCache<String, TransactionEntity> cache) {
    this.cache = cache;
    this.mapper = new TransactionMapper();
  }

  @Override
  public Mono<Transaction> findByTransactionId(TransactionId transactionId) {
    return Mono.fromCallable(() -> cache.getAsync(transactionId.value()).get())
        .map(mapper::fromEntity);
  }

  @Override
  public Mono<Transaction> save(Transaction requestTransaction) {
    return Mono.just(mapper.toEntity(requestTransaction))
        .flatMap(transaction -> Mono.fromCallable(() -> cache.putAsync(transaction.transactionId(), transaction).get()))
        .map(v -> requestTransaction);
  }
}
