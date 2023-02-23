package com.example.rabbitmq.consumer.model;


import com.example.rabbitmq.consumer.model.key.ItemKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemKey.class)
public class Item {
    @Id
    private String itemId,color,size;
    private String name;
    private int quantity,price;


    public Item(String itemId, String color, String size) {
        this.itemId = itemId;
        this.color = color;
        this.size = size;
    }
}
