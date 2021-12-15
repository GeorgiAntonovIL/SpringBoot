package com.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "tapUsers")
@Data
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private List<Address> addresses;
    private List<Transaction> transactions;



    public User() {
        this.addresses = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public User(String firstName, String lastName) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }


}
