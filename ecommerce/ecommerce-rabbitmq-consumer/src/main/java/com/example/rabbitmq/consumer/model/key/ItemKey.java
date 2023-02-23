package com.example.rabbitmq.consumer.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Embeddable
public class ItemKey implements Serializable {
    @Column(name = "item_id")
    private String itemId;
    @Column(name="size")
    private String size;
    @Column(name="color")
    private String color;
}
