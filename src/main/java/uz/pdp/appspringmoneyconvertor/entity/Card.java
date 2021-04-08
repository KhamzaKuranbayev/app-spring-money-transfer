package uz.pdp.appspringmoneyconvertor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;          // hozircha bitta user ga bitta karta qo'shib qo'ydim, aslida ko'p carta bo'lishi mumkin

    @Column(nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private Date expiredDate;

    private boolean active = true;

    public void withdraw(double balance) {
        this.balance -= balance;
    }

    public void deposit(double balance) {
        this.balance += balance;
    }

}
