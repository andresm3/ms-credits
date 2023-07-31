package com.bootcamp.banking.credits.infraestructure.repository;

import com.bootcamp.banking.credits.domain.models.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditRepository extends ReactiveMongoRepository<Credit, String>, CustomCreditRepository {

  Mono<Credit> findByProductId(String number);

  Mono<Credit> findByNumber(String number);
  Mono<Long> countByClientDocumentNumber(String documentNumber);
}
