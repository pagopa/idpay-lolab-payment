package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay;

import it.gov.pagopa.idpaylolabpayment.domain.TransactionId;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionIdGenerator;
import java.util.UUID;
import reactor.core.publisher.Mono;

public class UuidTransactionIdGenerator implements TransactionIdGenerator {

  @Override
  public Mono<TransactionId> generateId() {
    return Mono.just(new TransactionId(UUID.randomUUID().toString().substring(0, 7)));
  }
}
