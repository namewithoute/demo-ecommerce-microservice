package com.example.rabbitmq.consumer.controller;

import com.example.rabbitmq.consumer.dto.ItemDTO;
import com.example.rabbitmq.consumer.dto.ItemDetailDTO;
import com.example.rabbitmq.consumer.model.Item;
import com.example.rabbitmq.consumer.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value="/api/v1")
public class ItemController {
    @Autowired
    ItemRepo itemRepo;

    @GetMapping(value = "/items/{itemId}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable String itemId) throws Exception {
        ItemDTO itemDTO = new ItemDTO();
        List<ItemDetailDTO> itemDetailsDTO=new ArrayList<>();
        List<Item> items = itemRepo.findByItemId(itemId);
        System.out.println(items.size());
//        if(items.size()<=0){
//            throw new Exception("invalid item id");
//        }
        itemDTO.setItemId(items.get(0).getItemId());
        itemDTO.setName(items.get(0).getName());
        itemDTO.setPrice(items.get(0).getPrice());
        System.out.println(itemDTO.toString());

        for (Item item : items) {
            ItemDetailDTO itemDetailDTO = new ItemDetailDTO();
            itemDetailDTO.setColor(item.getColor());
            itemDetailDTO.setSize(item.getSize());
            itemDetailDTO.setQuantity(item.getQuantity());
            itemDetailsDTO.add(itemDetailDTO);
            itemDTO.setItemDetail(itemDetailsDTO);
        }
        return ResponseEntity.ok(itemDTO);
    }

    
}
