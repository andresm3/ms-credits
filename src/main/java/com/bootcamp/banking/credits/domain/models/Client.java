package com.bootcamp.banking.credits.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Client {

  @JsonProperty("id")
  private String id;
  private String documentType;
  private String documentNumber;
  private String firstName;
  private String lastName;
  private int type;
  private int profile;
  private boolean active;
  private String idClientCategory;
}
