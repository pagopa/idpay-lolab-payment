package it.gov.pagopa.idpaylolabpayment.features.keyvalue;

import org.apache.ignite.IgniteCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyValueModule {

  @Bean
  public PutKeyValue putKeyValue(IgniteCache<String, String> simpleCache) {
    return new PutKeyValue(simpleCache);
  }

  @Bean
  public GetValue getValue(IgniteCache<String, String> simpleCache) {
    return new GetValue(simpleCache);
  }
}
