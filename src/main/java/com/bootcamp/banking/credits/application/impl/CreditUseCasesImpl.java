package com.bootcamp.banking.credits.application.impl;

import com.bootcamp.banking.credits.application.CreditUseCases;
import com.bootcamp.banking.credits.application.exceptions.CustomInformationException;
import com.bootcamp.banking.credits.application.exceptions.CustomNotFoundException;
import com.bootcamp.banking.credits.application.utils.Constants;
import com.bootcamp.banking.credits.domain.models.Credit;
import com.bootcamp.banking.credits.domain.models.LoanDebt;
import com.bootcamp.banking.credits.infraestructure.repository.CreditRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditUseCasesImpl implements CreditUseCases {

  @Autowired
  private CreditRepository creditRepository;
  @Override
  public Mono<Credit> createCredit(Credit credit) {
    System.out.println(">>CreditUseCasesImpl=> createCredit");
    return creditRepository.findByNumber(credit.getProductId())
        .doOnNext(a -> Mono.error(new CustomInformationException("Credit number has already been created")))
        .switchIfEmpty(creditRepository
            .countByClientDocumentNumber(credit.getClient().getDocumentNumber())
            .map(a -> {
              if (credit.getClient().getType() == Constants.ClientType.PERSONAL && a > 0) {
                System.out.println(">>exception more than 1 personal credit ");
                return Mono.error(new CustomInformationException("The client type allows "
                    + "to have only 1 credits"));
              } else {
                return a;
              }
            })
            .then(Mono.just(credit))
            .flatMap(a -> creditRepository.save(a)
                .map(b -> {

                  System.out.println(">>Created a new id = {} for the account with number=> Saving " + b.getLoanDebt());
                  return b;
                })));
  }

  @Override
  public Mono<Credit> getCreditById(String id) {

    return creditRepository.findById(id)
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)));

  }

  @Override
  public Mono<Credit> getCreditByNumber(String number) {
    return creditRepository.findByNumber(number)
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)));

  }

  @Override
  public Flux<Credit> getCreditByClientName(String firstName, String lastName) {

    return creditRepository.findByClientFirstNameAndLastName(firstName, lastName)
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.FLUX_NOT_FOUND_MESSAGE)));

  }

  @Override
  public Mono<Credit> checkIfClientOwnsCreditCard(String documentNumber) {
    System.out.println("checkIfClientOwnsCreditCard: " + documentNumber);
    return creditRepository
        .findByClientDocumentNumberAndCreditType(documentNumber, Constants.CreditType.CARD)
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<BigDecimal> getCreditCardBalance(String number) {

    Mono<Credit> result = creditRepository.findByProductId(number)
        .switchIfEmpty(Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_FOUND_MESSAGE)));

    return result.flatMap(credit -> {
      if (credit.getType() == Constants.CreditType.CARD) {
        return Mono.just(credit.getCreditBalance());
      } else {
        return Mono
            .error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_CREDIT_CARD));
      }
    });
  }

  @Override
  public Mono<BigDecimal> checkIfClientHasDebts(String documentNumber) {

    return creditRepository.findByClientDocumentNumber(documentNumber)
        .switchIfEmpty(x -> Mono.error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_LOAN_DEBTS)))
        //.flatMap(item -> feeService.checkFeesExpired(item.getNumber()))
        .map(item -> this.isExpired(item.getLoanDebt()))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .switchIfEmpty(Mono.error(new CustomNotFoundException(Constants.CreditErrorMsg.MONO_NOT_LOAN_DEBTS)));

  }

  private BigDecimal isExpired(LoanDebt loanDebt) {
    if (loanDebt != null && loanDebt.getExpirationDate().isAfter(LocalDate.now()) && loanDebt.getStatus() == 1) {
      System.out.println("isExpired: 1");
      return new BigDecimal(1);
    }
    return new BigDecimal(0);
  }

//  private boolean isExpired(LoanDebt loanDebt) {
//    if(loanDebt.getExpirationDate().isAfter(LocalDate.now()) && loanDebt.getStatus() == 1 ){
//      return true;
//    }
//    return false;
//  }
}
