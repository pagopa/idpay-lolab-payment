package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.ignite;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo.TransactionMapper;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo.TransactionReactiveDao;
import java.util.concurrent.ExecutionException;
import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class TransactionMongoCacheAdapter extends CacheStoreAdapter<String, Transaction> {

  private final TransactionReactiveDao transactionReactiveDao;
  private final TransactionMapper transactionMapper;

  public TransactionMongoCacheAdapter(TransactionReactiveDao transactionReactiveDao) {
    this.transactionReactiveDao = transactionReactiveDao;
    this.transactionMapper = new TransactionMapper();
  }

  @Override
  public void write(Entry<? extends String, ? extends Transaction> entry)
      throws CacheWriterException {
    try {
      transactionReactiveDao.save(transactionMapper.toEntity(entry.getValue()))
          .subscribeOn(Schedulers.boundedElastic()).toFuture().get();
      log.info("Entry to save for key {}", entry.getKey());
    } catch (ExecutionException | InterruptedException e) {
      throw new CacheWriterException(e);
    }
  }

  @Override
  public void delete(Object key) throws CacheWriterException {
    try {
      transactionReactiveDao.deleteById(key.toString())
          .subscribeOn(Schedulers.boundedElastic()).toFuture().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new CacheWriterException(e);
    }
  }

  @Override
  public Transaction load(String key) throws CacheLoaderException {
    return transactionReactiveDao.findByTransactionId(key)
        .subscribeOn(Schedulers.boundedElastic())
        .blockOptional()
        .map(transactionMapper::fromEntity)
        .orElse(null);
  }

  @Override
  public void loadCache(IgniteBiInClosure<String, Transaction> clo, Object... args) {
    super.loadCache(clo, args);
  }
}
