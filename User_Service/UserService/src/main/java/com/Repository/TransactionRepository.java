package com.Repository;

import com.Entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
