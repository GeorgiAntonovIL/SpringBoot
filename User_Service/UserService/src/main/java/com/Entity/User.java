package com.Entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    private UUID id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions;



    public User() {
        this.addresses = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public User(String firstName, String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }


}
