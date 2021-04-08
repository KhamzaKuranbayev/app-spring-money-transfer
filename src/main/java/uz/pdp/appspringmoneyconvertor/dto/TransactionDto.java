package uz.pdp.appspringmoneyconvertor.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDto {

    private String toCardNumber;
    private double amount;
    private Date date;
    private Integer operationId;
}
