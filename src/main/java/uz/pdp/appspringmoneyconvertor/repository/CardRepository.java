package uz.pdp.appspringmoneyconvertor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appspringmoneyconvertor.entity.Card;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    boolean existsByCardNumber(String cardNumber);

    Optional<Card> findByUserId(UUID user_id);

    Optional<Card> findByCardNumber(String cardNumber);
}
