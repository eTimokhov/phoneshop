package com.es.core.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemInfo {
    @NotNull
    private Long phoneId;

    @NotNull
    @Min(0)
    private Long quantity;

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
