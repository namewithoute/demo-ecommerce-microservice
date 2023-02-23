package com.example.rabbitmq.consumer.repo;

import com.example.rabbitmq.consumer.model.Voucher;
import org.springframework.data.repository.CrudRepository;

public interface VoucherRepo extends CrudRepository<Voucher,String> {
}
