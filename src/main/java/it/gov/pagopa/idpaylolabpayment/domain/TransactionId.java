package it.gov.pagopa.idpaylolabpayment.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class TransactionId {
  private final String value;

  public String value() {
    return this.value;
  }
}