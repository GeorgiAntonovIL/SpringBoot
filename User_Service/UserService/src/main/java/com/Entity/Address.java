package com.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.UUID;

@Document
@Data
public class Address {
    @Id
    private UUID id;
    private String streetName;

    public Address(){
    }

}