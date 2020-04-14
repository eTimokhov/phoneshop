package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderForm;

import java.util.Optional;

public interface OrderService {
    Optional<Order> get(Long orderId);
    Order createOrder(OrderForm orderForm);
    void placeOrder(Order order) throws OutOfStockException;
}
