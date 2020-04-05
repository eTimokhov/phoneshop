package com.es.core.cart;

import com.es.core.order.OutOfStockException;

import java.util.List;

public interface CartService {

    Cart getCart();

    Long getTotalProductsQuantity();

    void addPhone(Long phoneId, Long quantity) throws OutOfStockException;

    /**
     * @return errors
     */
    List<String> update(List<CartItem> items);

    void remove(Long phoneId) throws OutOfStockException;
}
