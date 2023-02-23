package com.example.rabbitmq.consumer.repo;

import com.example.rabbitmq.consumer.model.Item;
import com.example.rabbitmq.consumer.model.key.ItemKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepo extends CrudRepository<Item, ItemKey> {
    List<Item> findByItemId(String itemId);
}
