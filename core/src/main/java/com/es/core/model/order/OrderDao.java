package com.es.core.model.order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    List<Order> getOrders();
    Optional<Order> get(Long id);
    void save(Order order);
}
