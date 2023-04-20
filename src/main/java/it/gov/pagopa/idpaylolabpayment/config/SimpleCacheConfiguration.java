package it.gov.pagopa.idpaylolabpayment.config;

import static it.gov.pagopa.ignitemember.IgniteMemberConfiguration.SIMPLE_CACHE_EVICTION_REGION;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleCacheConfiguration {

  @Bean
  public IgniteCache<String, String> simpleCache(Ignite ignite) {

    final var configuration = new CacheConfiguration<String, String>()
        .setName("simple-cache")
        .setCacheMode(CacheMode.PARTITIONED)
        .setOnheapCacheEnabled(true)
        .setBackups(1)
        .setDataRegionName(SIMPLE_CACHE_EVICTION_REGION);

    return ignite.getOrCreateCache(configuration);
  }

  @Bean
  public IgniteCache<String, Transaction> transactionCache(Ignite ignite) {
    final var configuration = new CacheConfiguration<String, Transaction>()
        .setName("transaction-cache")
        .setCacheMode(CacheMode.PARTITIONED)
        .setOnheapCacheEnabled(true)
        .setBackups(1)
        .setTypes(String.class, Transaction.class)
        .setDataRegionName(SIMPLE_CACHE_EVICTION_REGION);

    return ignite.getOrCreateCache(configuration);
  }

}
