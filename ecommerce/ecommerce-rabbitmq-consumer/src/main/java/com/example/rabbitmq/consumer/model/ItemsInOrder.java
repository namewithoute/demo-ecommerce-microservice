package com.example.rabbitmq.consumer.model;

import com.example.rabbitmq.consumer.model.key.ItemKey;
import com.example.rabbitmq.consumer.model.key.ItemsInOrderKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsInOrder  {
    @EmbeddedId
    private ItemsInOrderKey itemsInOrderKey;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="item_id",referencedColumnName="item_id",insertable=false, updatable=false),
            @JoinColumn(name = "color",referencedColumnName="color",insertable=false, updatable=false),
            @JoinColumn(name="size",referencedColumnName="size",insertable=false, updatable=false)})
    private Item item;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name="order_id",insertable=false, updatable=false)
    private Order order;

    private int quantity,price;


}
