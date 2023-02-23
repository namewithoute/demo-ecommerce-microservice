package com.example.rabbitmq.consumer.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private String itemId,name;
    private int price;
    private List<ItemDetailDTO> itemDetail;
}
