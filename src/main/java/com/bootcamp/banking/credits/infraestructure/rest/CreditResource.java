package com.bootcamp.banking.credits.infraestructure.rest;

import com.bootcamp.banking.credits.application.CreditUseCases;
import com.bootcamp.banking.credits.domain.models.Credit;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credits")
public class CreditResource {

  @Autowired
  private CreditUseCases creditUseCases;

  @PostMapping("/createCreditCard")
  public Mono<Credit> createCreditCard(@RequestBody Credit request) {
    // Credit credit = new Credit(request);
    System.out.println(">>CreditResource=> createCard ");
    return creditUseCases.createCredit(request);
  }

  @PostMapping("/createLoan")
  public Mono<Credit> createLoan(@RequestBody Credit request) {
    // Credit credit = new Credit(request);
    System.out.println(">>CreditResource=> createLoan ");
    return creditUseCases.createCredit(request);
  }
  @GetMapping("/id/{id}")
  public Mono<Credit> findById(@PathVariable String id) {
    return creditUseCases.getCreditById(id);
  }
  @GetMapping("/productId/{number}")
  public Mono<Credit> searchCreditByNumber(@PathVariable("number") String number) {
    return creditUseCases.getCreditByNumber(number);
  }
  @GetMapping("/client/firstName/{firstName}/lastName/{lastName}")
  public Flux<Credit> findByCreditFirstNameAndLastName(@PathVariable String firstName,
      @PathVariable String lastName) {
    return creditUseCases.getCreditByClientName(firstName, lastName);
  }
  @GetMapping("/clientOwnsCard/{documentNumber}")
  public Mono<Credit> checkIfClientOwnsCreditCard(
      @PathVariable("documentNumber") String documentNumber) {
    return creditUseCases.checkIfClientOwnsCreditCard(documentNumber);
  }

  @GetMapping("/getBalance/{number}")
  public Mono<BigDecimal> getCreditCardBalance(@PathVariable("number") String number) {
    return creditUseCases.getCreditCardBalance(number);
  }
  @GetMapping("/check/debts/{documentNumber}")
  public Mono<BigDecimal> checkIfClientHasDebs(@PathVariable String documentNumber) {
    return creditUseCases.checkIfClientHasDebts(documentNumber);
  }
}
