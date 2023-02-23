package com.example.rabbitmq.consumer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voucher {
    @Id
    private String voucherCode;

    private double discount;
    private Date expireDate;
    private int quantity;
    private Date startAt;
}
// getters and setters

