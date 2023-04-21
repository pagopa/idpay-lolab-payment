package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo;

import it.gov.pagopa.idpaylolabpayment.domain.TransactionRepository;
import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionId;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MongoTransactionRepository implements TransactionRepository {

  private final TransactionReactiveDao transactionReactiveDao;
  private final TransactionMapper transactionMapper;

  public MongoTransactionRepository(TransactionReactiveDao transactionReactiveDao) {
    this.transactionReactiveDao = transactionReactiveDao;
    this.transactionMapper = new TransactionMapper();
  }

  @Override
  public Mono<Transaction> findByTransactionId(TransactionId transactionId) {
    return transactionReactiveDao.findByTransactionId(transactionId.value())
        .doOnEach(t -> log.info("Found {}", t.hasValue()))
        .map(transactionMapper::fromEntity);
  }

  @Override
  public Mono<Transaction> save(Transaction requestTransaction) {
    return Mono.just(transactionMapper.toEntity(requestTransaction))
        .flatMap(transactionReactiveDao::save)
        .map(save -> requestTransaction);
  }
}
