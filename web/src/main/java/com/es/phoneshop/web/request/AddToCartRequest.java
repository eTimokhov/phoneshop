package com.es.phoneshop.web.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddToCartRequest {

    @NotNull
    private Long phoneId;

    @NotNull
    @Min(value = 1)
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
