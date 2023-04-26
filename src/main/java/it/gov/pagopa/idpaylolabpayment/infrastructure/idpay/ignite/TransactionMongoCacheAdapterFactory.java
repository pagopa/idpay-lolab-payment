package it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.ignite;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.infrastructure.idpay.mongo.TransactionReactiveDao;
import java.io.Serializable;
import java.util.Objects;
import javax.cache.configuration.Factory;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.internal.IgniteComponentType;
import org.apache.ignite.internal.util.spring.IgniteSpringHelper;
import org.apache.ignite.resources.SpringApplicationContextResource;

public class TransactionMongoCacheAdapterFactory implements Factory<CacheStoreAdapter<String, Transaction>>, Serializable {

  private static final long serialVersionUID = -1662481364440098870L;

  @SpringApplicationContextResource
  private Object appCtx;

  private String dataSourceBean;

  @Override
  public CacheStoreAdapter<String, Transaction> create() {
    IgniteSpringHelper spring;

    try {
      spring = IgniteComponentType.SPRING.create(false);
      TransactionReactiveDao dao = spring.loadBeanFromAppContext(appCtx, dataSourceBean);
      return new TransactionMongoCacheAdapter(dao);
    } catch (IgniteCheckedException e) {
      throw new IgniteException("Failed to load bean in application context");
    }
  }

  public TransactionMongoCacheAdapterFactory setDataSourceBean(String dataSourceBean) {
    this.dataSourceBean = dataSourceBean;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionMongoCacheAdapterFactory that = (TransactionMongoCacheAdapterFactory) o;
    return Objects.equals(appCtx, that.appCtx) && Objects.equals(dataSourceBean,
        that.dataSourceBean);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appCtx, dataSourceBean);
  }
}