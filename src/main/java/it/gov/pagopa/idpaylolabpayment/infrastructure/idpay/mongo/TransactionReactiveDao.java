package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Mono;


@EnableReactiveMongoRepositories
public interface TransactionReactiveDao extends ReactiveMongoRepository<TransactionEntity, String> {
  Mono<TransactionEntity> findByTransactionId(String transactionId);
}
