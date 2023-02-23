package com.example.rabbitmq.consumer.mqConsumer;

import com.example.rabbitmq.consumer.dto.ItemDTO;
import com.example.rabbitmq.consumer.dto.ItemDetailDTO;
import com.example.rabbitmq.consumer.dto.ItemsInOrderDTO;
import com.example.rabbitmq.consumer.model.Item;
import com.example.rabbitmq.consumer.repo.ItemRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerItem {
    @Autowired
    private ItemRepo itemRepo;
    @RabbitListener(queues = "addNewItem")
    public void consumeItem(String item) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ItemDTO itemDTO=mapper.readValue(item,ItemDTO.class);
        System.out.println(itemDTO);
        for(ItemDetailDTO itemDetail: itemDTO.getItemDetail()){
            Item itemModel =new Item();
            itemModel.setItemId(itemDTO.getItemId());
            itemModel.setName(itemDTO.getName());
            itemModel.setPrice(itemDTO.getPrice());
            itemModel.setSize(itemDetail.getSize());
            itemModel.setColor(itemDetail.getColor());
            itemModel.setQuantity(itemDetail.getQuantity());
            itemRepo.save(itemModel);
        }
        System.out.println("done");

    }

}
