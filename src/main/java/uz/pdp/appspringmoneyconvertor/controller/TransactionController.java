package uz.pdp.appspringmoneyconvertor.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringmoneyconvertor.dto.TransactionDto;
import uz.pdp.appspringmoneyconvertor.entity.Response;
import uz.pdp.appspringmoneyconvertor.entity.Transaction;
import uz.pdp.appspringmoneyconvertor.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public HttpEntity<?> doTransaction(@RequestBody TransactionDto transactionDto) {
        Response response = transactionService.doTransaction(transactionDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        List<Transaction> transactionList = transactionService.findAll();
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> findOneById(@PathVariable Integer id) {
        Transaction transaction = transactionService.findOneById(id);
        if(transaction != null) {
            return ResponseEntity.ok(transaction);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Such transaction id not found!", false));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Integer id) {
        Response response = transactionService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).body(response);
    }

}
