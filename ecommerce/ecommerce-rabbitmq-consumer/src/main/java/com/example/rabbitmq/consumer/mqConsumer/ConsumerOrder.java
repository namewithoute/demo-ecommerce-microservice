package com.example.rabbitmq.consumer.mqConsumer;

import com.example.rabbitmq.consumer.dto.ItemsInOrderDTO;
import com.example.rabbitmq.consumer.dto.OrderDTO;
import com.example.rabbitmq.consumer.dto.VoucherDTO;
import com.example.rabbitmq.consumer.model.*;
import com.example.rabbitmq.consumer.model.key.ItemsInOrderKey;
import com.example.rabbitmq.consumer.repo.ItemsInOrderRepo;
import com.example.rabbitmq.consumer.repo.OrderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsumerOrder {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ItemsInOrderRepo itemsInOrderRepo;
    ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "createOrder")
    public void consumerOrder(String order, Channel channel) throws JsonProcessingException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OrderDTO orderDTO = mapper.readValue(order, OrderDTO.class);
        Order orderModel = mapper.readValue(order, Order.class);

        orderRepo.save(orderModel);
        for (ItemsInOrderDTO itemInOrderDTO : orderDTO.getItemList()) {
            Item item = new Item(itemInOrderDTO.getId(), itemInOrderDTO.getColor(), itemInOrderDTO.getSize());

            ItemsInOrderKey itemKey = new ItemsInOrderKey(orderModel.getOrderId(), itemInOrderDTO.getId(), itemInOrderDTO.getColor(), itemInOrderDTO.getSize());

            ItemsInOrder itemsInOrder = new ItemsInOrder(itemKey, item, orderModel, itemInOrderDTO.getQuantity(), itemInOrderDTO.getPrice());
            System.out.print(itemsInOrder);

            itemsInOrderRepo.save(itemsInOrder);
        }

    }


}