package it.gov.pagopa.ignitemember;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteSpring;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.processors.cache.persistence.wal.reader.StandaloneNoopCommunicationSpi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteMemberConfiguration {

  @Bean
  public Ignite ignite(
      IgniteConfiguration memberConfiguration,
      ApplicationContext applicationContext
  ) throws IgniteCheckedException {
    return IgniteSpring.start(memberConfiguration, applicationContext);
  }

  @Bean
  public IgniteConfiguration memberConfiguration() {
    return new IgniteConfiguration()
        .setIgniteInstanceName("ignite-cluster")
        .setConsistentId("consistendId")
        .setCommunicationSpi(new StandaloneNoopCommunicationSpi());
  }
}
