package com.bootcamp.banking.credits.infraestructure.repository;

import com.bootcamp.banking.credits.domain.models.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomCreditRepository {

  Flux<Credit> findByClientFirstNameAndLastName(String firstName, String lastName);

  Flux<Credit> findByClientDocumentNumber(String documentNumber);

  Mono<Credit> findByClientDocumentNumberAndCreditType(String documentNumber, Integer creditType);
}
