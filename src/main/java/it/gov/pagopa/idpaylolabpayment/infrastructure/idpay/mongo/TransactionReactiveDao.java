package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TransactionReactiveDao extends ReactiveMongoRepository<TransactionEntity, String> {
  Mono<TransactionEntity> findByTransactionId(String transactionId);
}
