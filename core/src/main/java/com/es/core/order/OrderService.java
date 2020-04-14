package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderForm;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getOrders();
    Optional<Order> get(Long orderId);
    Order createOrder(OrderForm orderForm);
    void updateOrder(Order order);
    void placeOrder(Order order) throws OutOfStockException;
}
