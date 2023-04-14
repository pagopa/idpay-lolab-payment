package it.gov.pagopa.idpaylolabpayment.features.keyvalue;

import org.apache.ignite.IgniteCache;
import reactor.core.publisher.Mono;

public class GetValue {

  private final IgniteCache<String, String> cache;

  public GetValue(IgniteCache<String, String> cache) {
    this.cache = cache;
  }

  public Mono<String> handle(String key) {
    return Mono.fromCallable(() -> cache.getAsync(key).get());
  }
}
