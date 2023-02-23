package com.example.rabbitmq.consumer.dto;

import com.example.rabbitmq.consumer.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsInOrderDTO{
    private String id,size,color,name;

    private int quantity,price;
}
