package com.es.core.cart;

import com.es.core.model.phone.Phone;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItem {
    @NotNull
    private Phone phone;

    @NotNull
    @Min(0)
    private Long quantity;

    public CartItem(Phone phone, Long quantity) {
        this.phone = phone;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public CartItem(CartItem cartItem) {
        this.phone = cartItem.phone;
        this.quantity = cartItem.quantity;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
