package com.es.core.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderForm;
import com.es.core.model.order.OrderStatus;

import java.util.List;

public interface OrderService {
    List<Order> getOrders();
    Order get(Long orderId);
    Order createOrder(OrderForm orderForm);
    void updateOrder(Order order);
    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
    void placeOrder(Order order) throws OutOfStockException;
}
