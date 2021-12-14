package com.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "tap")
@Data
public class Transaction {

    @Id
    private UUID id;
    private LocalDateTime dateOfExecution;

}