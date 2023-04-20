package it.gov.pagopa.idpaylolabpayment.features.idpay;

import it.gov.pagopa.idpaylolabpayment.domain.TransactionIdGenerator;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionRepository;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.UuidTransactionIdGenerator;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo.MongoTransactionRepository;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo.TransactionReactiveDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdpayModule {

  @Bean
  AddRequestTransaction addRequestTransaction(
      TransactionRepository repository,
      TransactionIdGenerator transactionIdGenerator
  ) {
    return new AddRequestTransaction(repository, transactionIdGenerator);
  }

  @Bean
  GetRequestTransaction getRequestTransaction(TransactionRepository repository) {
    return new GetRequestTransaction(repository);
  }

  @Bean
  TransactionIdGenerator transactionIdGenerator() {
    return new UuidTransactionIdGenerator();
  }

  @Bean
  TransactionRepository repository(TransactionReactiveDao transactionReactiveDao) {
    return new MongoTransactionRepository(transactionReactiveDao);
  }

}
