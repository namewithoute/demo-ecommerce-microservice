package com.example.rabbitmq.consumer.repo;

import com.example.rabbitmq.consumer.model.key.ItemKey;
import com.example.rabbitmq.consumer.model.key.ItemsInOrderKey;
import org.springframework.data.repository.CrudRepository;
import com.example.rabbitmq.consumer.model.ItemsInOrder;

public interface ItemsInOrderRepo extends CrudRepository<ItemsInOrder, ItemsInOrderKey> {
}
