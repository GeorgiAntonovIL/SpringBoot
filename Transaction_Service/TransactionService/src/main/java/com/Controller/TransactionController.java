package com.Controller;

import com.Service.TransactionService;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction/create")
    public String createTransaction(@RequestParam UUID userId) {
        return transactionService.createTransaction(userId);
    }

    @GetMapping("/transaction/all/{userId}")
    public String getAllTransactions(@PathVariable UUID userId){
        return transactionService.getAll(userId);
    }

}
