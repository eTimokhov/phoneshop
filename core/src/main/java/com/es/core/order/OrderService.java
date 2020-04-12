package com.es.core.order;

import com.es.core.model.order.Order;

import java.util.Optional;

public interface OrderService {
    Optional<Order> get(Long orderId);
    Order createOrder();
    void placeOrder(Order order) throws OutOfStockException;
}
