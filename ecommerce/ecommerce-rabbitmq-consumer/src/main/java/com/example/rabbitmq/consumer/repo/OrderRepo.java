package com.example.rabbitmq.consumer.repo;

import org.springframework.data.repository.CrudRepository;
import com.example.rabbitmq.consumer.model.Order;
public interface OrderRepo extends CrudRepository<Order,String> {
}
