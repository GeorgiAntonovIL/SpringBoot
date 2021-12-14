package com.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@Table(name = "addresses")
public class Address {
    @Id
    private UUID id;
    private String streetName;

    public Address(){
    }

}