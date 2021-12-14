package com.Service;

import org.springframework.stereotype.Service;

import java.util.UUID;


public interface TransactionService {

    String createTransaction(UUID userId);

    String getAll(UUID userId);
}
