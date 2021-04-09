package uz.pdp.appspringmoneyconvertor.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appspringmoneyconvertor.dto.CardDto;
import uz.pdp.appspringmoneyconvertor.entity.Card;
import uz.pdp.appspringmoneyconvertor.entity.Response;
import uz.pdp.appspringmoneyconvertor.entity.User;
import uz.pdp.appspringmoneyconvertor.repository.CardRepository;
import uz.pdp.appspringmoneyconvertor.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    final CardRepository cardRepository;
    final UserRepository userRepository;

    public CardServiceImpl(CardRepository cardRepository,
                           UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Response save(CardDto cardDto) {

        if(cardRepository.existsByCardNumber(cardDto.getCardNumber()))
            return new Response("Card number is already exists!", false);

        Card card = new Card();
        card.setCardNumber(cardDto.getCardNumber());
        card.setExpiredDate(cardDto.getExpiredDate());
        card.setActive(cardDto.isActive());
        card.setBalance(cardDto.getBalance());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(username);
        optionalUser.ifPresent(card::setUser);

        cardRepository.save(card);

        return new Response("Card saved!", true);
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

}
