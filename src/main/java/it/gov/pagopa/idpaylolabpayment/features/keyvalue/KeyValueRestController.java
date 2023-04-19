package it.gov.pagopa.idpaylolabpayment.features.keyvalue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/kv")
public class KeyValueRestController {

  private final PutKeyValue putKeyValue;
  private final GetValue getValue;

  public KeyValueRestController(PutKeyValue putKeyValue, GetValue getValue) {
    this.putKeyValue = putKeyValue;
    this.getValue = getValue;
  }

  @PutMapping("/{key}")
  public Mono<String> putKeyValue(@PathVariable("key") String key, @RequestBody String value) {
    return putKeyValue.handle(key, value);
  }

  @GetMapping("/{key}")
  public Mono<String> putKeyValue(@PathVariable("key") String key) {
    return getValue.handle(key);
  }

}
