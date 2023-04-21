package it.gov.pagopa.idpaylolabpayment.features.idpay;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionIdGenerator;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionRepository;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.UuidTransactionIdGenerator;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.ignite.IgniteTransactionRepository;
import org.apache.ignite.IgniteCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdpayModule {

  @Bean
  AddPendingTransaction addRequestTransaction(
      TransactionRepository repository,
      TransactionIdGenerator transactionIdGenerator
  ) {
    return new AddPendingTransaction(repository, transactionIdGenerator);
  }

  @Bean
  GetRequestTransaction getRequestTransaction(TransactionRepository repository) {
    return new GetRequestTransaction(repository);
  }

  @Bean
  TransactionIdGenerator transactionIdGenerator() {
    return new UuidTransactionIdGenerator();
  }

//  @Bean
//  TransactionRepository repository(TransactionReactiveDao transactionReactiveDao) {
//    return new MongoTransactionRepository(transactionReactiveDao);
//  }

  @Bean
  TransactionRepository repository(IgniteCache<String, Transaction> transactionCache) {
    return new IgniteTransactionRepository(transactionCache);
  }
}
