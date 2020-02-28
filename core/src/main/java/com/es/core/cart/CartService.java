package com.es.core.cart;

import com.es.core.order.OutOfStockException;

import java.util.Map;

public interface CartService {

    Cart getCart();

    Long getTotalProductsQuantity();

    void addPhone(Long phoneId, Long quantity) throws OutOfStockException;

    /**
     * @param items
     * key: {@link com.es.core.model.phone.Phone#id}
     * value: quantity
     */
    void update(Map<Long, Long> items);

    void remove(Long phoneId) throws OutOfStockException;
}
