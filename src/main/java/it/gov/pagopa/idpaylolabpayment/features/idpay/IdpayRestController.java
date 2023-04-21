package it.gov.pagopa.idpaylolabpayment.features.idpay;

import it.gov.pagopa.idpaylolabpayment.domain.Transaction;
import it.gov.pagopa.idpaylolabpayment.domain.TransactionId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/transaction")
@Slf4j
public class IdpayRestController {

  private final AddPendingTransaction addPendingTransaction;
  private final GetRequestTransaction getRequestTransaction;

  public IdpayRestController(
      AddPendingTransaction addPendingTransaction,
      GetRequestTransaction getRequestTransaction
  ) {
    this.addPendingTransaction = addPendingTransaction;
    this.getRequestTransaction = getRequestTransaction;
  }

  @PostMapping("/")
  public Mono<ResponseEntity<String>> addTransaction(@RequestBody RequestTransactionDto request) {
    log.info("Request create transaction with {}", request);
    return addPendingTransaction.handle(request)
        .map(TransactionId::value)
        .map(ResponseEntity::ok)
        .doOnError(error -> log.error("Error during create request", error))
        .defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  @GetMapping("/{id}")
  public @ResponseBody Mono<ResponseEntity<Transaction>> getTransaction(@PathVariable("id") String transactionId) {
    log.info("Request transaction with {}", transactionId);
    return getRequestTransaction.handle(new TransactionId(transactionId))
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
