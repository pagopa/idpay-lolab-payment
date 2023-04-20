package it.gov.pagopa.idpaylolabpayment.domain;

import reactor.core.publisher.Mono;

public interface TransactionIdGenerator {
  Mono<TransactionId> generateId();
}
