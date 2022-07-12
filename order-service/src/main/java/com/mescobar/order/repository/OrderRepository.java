package com.mescobar.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mescobar.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
