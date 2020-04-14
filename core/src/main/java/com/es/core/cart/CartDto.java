package com.es.core.cart;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private List<CartItem> cartItems;
    private BigDecimal deliveryPrice;
    private BigDecimal subtotal;
    private BigDecimal totalPrice;

    public CartDto() {
    }

    public CartDto(Cart cart) {
        cartItems = cart.getCartItems();
        deliveryPrice = cart.getDeliveryPrice();
        subtotal = cart.getSubtotal();
        totalPrice = cart.getTotalPrice();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
