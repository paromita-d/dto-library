package txn.controller;

import org.springframework.web.bind.annotation.*;
import txn.dto.Cart;
import txn.dto.TxnType;
import txn.service.TransactionService;

import java.util.Map;

@RestController
@RequestMapping(path = "/")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path="/{txnType}/{id}", produces = "application/json")
    public Cart transactCart(@PathVariable TxnType txnType, @RequestParam Map<String, String> items, @PathVariable int id) {
        return transactionService.transactItems(txnType, items, id);
    }
}
