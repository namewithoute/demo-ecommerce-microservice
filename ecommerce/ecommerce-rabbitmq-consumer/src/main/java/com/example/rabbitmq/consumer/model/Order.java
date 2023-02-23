package com.example.rabbitmq.consumer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "`order`")

public class Order {
    @Id
    private String orderId;
    private String email;
    private String paymentMethod;
    private boolean isPaid;
//    private List<ItemDTO> itemList;
    private int subtotal;
    private int total;
    private int shippingFee;
    private String shippingAddress;
    private String note;
//    private VoucherDTO discount;
    private boolean isCancel;
    private Date createAt;

    public Order() {
        this.orderId= UUID.randomUUID().toString();
    }


    public Order(String email, String paymentMethod, boolean isPaid, int subtotal, int total, int shippingFee, String shippingAddress, String note, boolean isCancel, Date createAt) {
        this.orderId=UUID.randomUUID().toString();
        this.email = email;
        this.paymentMethod = paymentMethod;
        this.isPaid = isPaid;
        this.subtotal = subtotal;
        this.total = total;
        this.shippingFee = shippingFee;
        this.shippingAddress = shippingAddress;
        this.note = note;
        this.isCancel = isCancel;
        this.createAt = createAt;
    }
}
