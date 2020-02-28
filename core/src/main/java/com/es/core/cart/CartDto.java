package com.es.core.cart;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private List<CartItem> cartItems;
    private BigDecimal totalPrice;

    public CartDto(Cart cart) {
        cartItems = cart.getCartItems();
        totalPrice = cart.getTotalPrice();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
