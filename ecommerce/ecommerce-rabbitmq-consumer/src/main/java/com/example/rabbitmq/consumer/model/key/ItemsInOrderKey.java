package com.example.rabbitmq.consumer.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class ItemsInOrderKey implements Serializable {
    @Column(name = "order_id",length = 20)
    private String orderId;
    @Column(name="item_id",length = 20)
    private String itemId;
    @Column(length = 20)
    private String color;
    @Column(length = 20)
    private String size;
}
