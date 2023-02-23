package com.example.rabbitmq.consumer.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
        private String orderID;
        private String email;
        private String paymentMethod;
        private boolean isPaid;
        @JsonValue
        private List<ItemsInOrderDTO> itemList;
        private int subtotal;
        private int total;
        private int shippingFee;
        private String shippingAddress;
        private String note;
        private VoucherDTO discount;
        private boolean isCancel;
        private Date createAt;

        // getters and setters

}
