package uz.pdp.appspringmoneyconvertor.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringmoneyconvertor.dto.CardDto;
import uz.pdp.appspringmoneyconvertor.entity.Response;
import uz.pdp.appspringmoneyconvertor.service.CardService;

@RestController
@RequestMapping("/api/card")
public class CardController {

    final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping()
    public HttpEntity<?> save(@RequestBody CardDto cardDto) {
        Response save = cardService.save(cardDto);
        return ResponseEntity.status(save.isSuccess() ? HttpStatus.CREATED : HttpStatus.UNAUTHORIZED).body(save);
    }


}
