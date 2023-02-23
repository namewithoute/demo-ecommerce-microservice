package com.example.rabbitmq.consumer.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class MQConfig {
    public static final String QUEUE_NAME="createOrder";
    public static final String EXCHANGE_NAME="exchange_create_order";
    public static final String ROUTING_KEY="routing_key_create_order";

    public Queue queue(){
        return new Queue(QUEUE_NAME);
    }
    public TopicExchange exchange(){
        return new TopicExchange("message_exchange");
    }
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template= new RabbitTemplate((org.springframework.amqp.rabbit.connection.ConnectionFactory) connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
