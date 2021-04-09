package uz.pdp.appspringmoneyconvertor.service;

import org.springframework.stereotype.Service;
import uz.pdp.appspringmoneyconvertor.dto.CardDto;
import uz.pdp.appspringmoneyconvertor.entity.Card;
import uz.pdp.appspringmoneyconvertor.entity.Response;

import java.util.List;

@Service
public interface CardService {

    Response save(CardDto cardDto);

    List<Card> findAll();

}
