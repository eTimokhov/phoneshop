package com.es.core.cart;

import com.es.core.order.OutOfStockException;

public interface CartService {

    Cart getCart();

    Long getTotalProductsQuantity();

    void addPhone(Long phoneId, Long quantity) throws OutOfStockException;

    void update(CartItem cartItem) throws OutOfStockException;

    void remove(Long phoneId) throws OutOfStockException;
}
