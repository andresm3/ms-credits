package com.bootcamp.banking.credits.application;

import com.bootcamp.banking.credits.domain.models.Credit;
import java.math.BigDecimal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditUseCases {

  Mono<Credit> createCredit(Credit credit);
  Mono<Credit> getCreditById(String id);
  Mono<Credit> getCreditByNumber(String number);
  Flux<Credit> getCreditByClientName(String firstName, String lastName);
  Mono<Credit> checkIfClientOwnsCreditCard(String documentNumber);
  Mono<BigDecimal> getCreditCardBalance(String number);
  Mono<BigDecimal> checkIfClientHasDebts(String documentNumber);


}
