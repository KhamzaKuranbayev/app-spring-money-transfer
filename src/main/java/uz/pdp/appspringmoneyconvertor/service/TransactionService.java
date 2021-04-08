package uz.pdp.appspringmoneyconvertor.service;

import org.springframework.stereotype.Service;
import uz.pdp.appspringmoneyconvertor.dto.TransactionDto;
import uz.pdp.appspringmoneyconvertor.entity.Response;
import uz.pdp.appspringmoneyconvertor.entity.Transaction;

import java.util.List;

@Service
public interface TransactionService {

    Response doTransaction(TransactionDto transactionDto);

    List<Transaction> findAll();

    Transaction findOneById(Integer transactionId);

    Response delete(Integer transactionId);

}
