package it.gov.pagopa.idpaylolabpayment.features.keyvalue;

import org.apache.ignite.IgniteCache;
import reactor.core.publisher.Mono;

public class PutKeyValue {

  private final IgniteCache<String, String> cache;

  public PutKeyValue(IgniteCache<String, String> cache) {
    this.cache = cache;
  }

  public Mono<String> handle(String key, String value) {
    return Mono.fromCallable(() -> cache.putAsync(key, value).get())
        .map(it -> value);
  }
}
