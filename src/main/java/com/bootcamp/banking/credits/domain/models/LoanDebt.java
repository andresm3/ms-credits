package com.bootcamp.banking.credits.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDebt {

  private String id;
  private String clientDocumentNumber;
  private String productId;
  @JsonFormat(pattern = "dd/MM/yyyy")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private LocalDate expirationDate;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal amount;
  private int status;
}
