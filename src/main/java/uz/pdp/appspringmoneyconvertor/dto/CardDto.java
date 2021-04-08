package uz.pdp.appspringmoneyconvertor.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CardDto {

    private String cardNumber;
    private double balance;
    private Date expiredDate;
    private boolean active;

}
