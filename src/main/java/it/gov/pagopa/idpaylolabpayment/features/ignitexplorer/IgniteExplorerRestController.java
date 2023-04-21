package it.gov.pagopa.idpaylolabpayment.features.ignitexplorer;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.cache.Cache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.lang.IgniteCallable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ignite")
public class IgniteExplorerRestController {

  private final Ignite ignite;
  private final IgniteCache<String, Transaction> transactionIgniteCache;

  public IgniteExplorerRestController(Ignite ignite,
      IgniteCache<String, Transaction> transactionIgniteCache) {
    this.ignite = ignite;
    this.transactionIgniteCache = transactionIgniteCache;
  }

  @GetMapping("/cache/transaction")
  Mono<ResponseEntity<Map<String, Map<String, List<String>>>>> getTransactionDataOnEachNode() {
    final var group = ignite.cluster().forCacheNodes("transaction-cache").forServers();
    final var collectedEntries = ignite.compute(group)
        .broadcast(new DumpTask(ignite, transactionIgniteCache))
        .stream()
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    return Mono.just(ResponseEntity.ok(collectedEntries));
  }

  static class DumpTask implements IgniteCallable<Entry<String, Map<String, List<String>>>> {
    private final Ignite ignite;
    private final IgniteCache<String, Transaction> transactionIgniteCache;


    DumpTask(Ignite ignite, IgniteCache<String, Transaction> transactionIgniteCache) {
      this.ignite = ignite;
      this.transactionIgniteCache = transactionIgniteCache;
    }

    @Override
    public Entry<String, Map<String, List<String>>> call() throws Exception {
      final var dataNodeStatus = Stream.of(CachePeekMode.PRIMARY, CachePeekMode.BACKUP, CachePeekMode.OFFHEAP)
          .map(it -> Map.entry(it.name(), dumpKeys(it)))
          .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      return Map.entry(ignite.cluster().localNode().id().toString(), dataNodeStatus);
    }

    private List<String> dumpKeys(CachePeekMode cachePeekMode) {
      return StreamSupport.stream(
              transactionIgniteCache.localEntries(cachePeekMode).spliterator(), false)
          .map(Cache.Entry::getKey)
          .toList();
    }
  }
}
