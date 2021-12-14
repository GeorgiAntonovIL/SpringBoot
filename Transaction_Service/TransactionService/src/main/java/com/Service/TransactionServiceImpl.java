package com.Service;

import com.Entity.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class TransactionServiceImpl implements TransactionService {

    private final Map<UUID, List<Transaction>> transactionMap;
    private final Gson gson;

    public TransactionServiceImpl(Gson gson) {
        this.gson = gson;
        this.transactionMap = new HashMap<>();
    }


    @Override
    public String createTransaction(UUID userId) {

        LocalDateTime dateOfExecution = generateDate();
        String transId = generateId();

        Transaction transaction = new Transaction();
        transaction.setId(UUID.fromString(transId));
        transaction.setDateOfExecution(dateOfExecution);

        transactionMap.putIfAbsent(userId, new ArrayList<>());
        transactionMap.get(userId).add(transaction);

        return gson.toJson(transaction);
    }

    @Override
    public String getAll(UUID userId) {
        transactionMap.get(userId);
        ObjectMapper objectMapper = new ObjectMapper();

        String resultJson = "";
        try {
            resultJson = objectMapper.writeValueAsString(transactionMap.get(userId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJson;
    }



    public LocalDateTime generateDate(){
        LocalDateTime now = LocalDateTime.now();
        return now;
    }
    public String generateId(){
        UUID uuid = UUID.randomUUID();
        String result = uuid.toString();
        return result;
    }
}
