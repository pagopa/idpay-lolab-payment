package it.gov.pagopa.idpaylolabpayment.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleCacheConfiguration {

 @Bean
    public IgniteCache<String, String> simpleCache(
      Ignite ignite
 ) {
    final var configuration = new CacheConfiguration<String, String>()
        .setName("simple-cache")
        .setCacheMode(CacheMode.PARTITIONED)
        .setOnheapCacheEnabled(true);

    return ignite.getOrCreateCache(configuration);
  }

}
