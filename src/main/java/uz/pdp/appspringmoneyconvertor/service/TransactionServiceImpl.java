package uz.pdp.appspringmoneyconvertor.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appspringmoneyconvertor.dto.TransactionDto;
import uz.pdp.appspringmoneyconvertor.entity.*;
import uz.pdp.appspringmoneyconvertor.repository.CardRepository;
import uz.pdp.appspringmoneyconvertor.repository.OperationRepository;
import uz.pdp.appspringmoneyconvertor.repository.TransactionRepository;
import uz.pdp.appspringmoneyconvertor.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {


    final TransactionRepository transactionRepository;
    final UserRepository userRepository;
    final CardRepository cardRepository;
    final OperationRepository operationRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  UserRepository userRepository,
                                  CardRepository cardRepository,
                                  OperationRepository operationRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Response doTransaction(TransactionDto transactionDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (!optionalUser.isPresent())
            return new Response("User not found!", false);

        UUID id = optionalUser.get().getId();

        Optional<Card> optionalCardFrom = cardRepository.findByUserId(id);
        if (!optionalCardFrom.isPresent())
            return new Response("The Sender's Card was not found!", false);


        Optional<Operation> optionalOperation = operationRepository.findById(transactionDto.getOperationId());
        if (!optionalOperation.isPresent())
            return new Response("Such Operation was not found!", false);

        float commission;
        commission = optionalOperation.get().getCommission();
        double transfer_money = transactionDto.getAmount() + (transactionDto.getAmount() * commission) / 100;

        if (optionalCardFrom.get().getBalance() < transfer_money)
            return new Response("Sorry! Not enough money in your balance", false);

        if(!cardRepository.existsByCardNumber(transactionDto.getToCardNumber()))
            return new Response("Such card number was not found!", false);

        Optional<Card> optionalCardTo = cardRepository.findByCardNumber(transactionDto.getToCardNumber());
        if (!optionalCardTo.isPresent())
            return new Response("The Receiver's card was not found!", false);

        Transaction transaction = new Transaction();
        transaction.setFromCard(optionalCardFrom.get());
        transaction.setAmount(transfer_money);
        transaction.setDate(transactionDto.getDate());
        transaction.setOperation(optionalOperation.get());
        transaction.setToCard(optionalCardTo.get());

        optionalCardFrom.get().withdraw(transfer_money);        //  O'tkazuvchidan pul yechish
        optionalCardTo.get().deposit(transfer_money);          //  Qabul qiluvchiga pul o'tkazish

        cardRepository.save(optionalCardFrom.get());            // O'TKAZUVCHI KARTASI UPDATE QILINDI
        cardRepository.save(optionalCardTo.get());              // QABUL QILUVCHI KARTASI UPDATE QILINDI

        transactionRepository.save(transaction);

        return new Response("The Transaction was successful! Receiver Card Owner: "
                + optionalCardTo.get().getUser().getFirstname() + " "
                + optionalCardTo.get().getUser().getLastname(), true);
    }


    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findOneById(Integer transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        return optionalTransaction.orElse(null);
    }

    @Override
    public Response delete(Integer transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isPresent()) {
            transactionRepository.deleteById(transactionId);
            return new Response("Transaction deleted!", true);
        }
        return new Response("Such transaction id not found!", false);
    }
}
