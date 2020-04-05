package com.es.core.cart;

import com.es.core.cart.validator.ValidQuantity;
import com.es.core.model.phone.Phone;

import javax.validation.constraints.NotNull;

public class CartItemInfo {

    @NotNull
    private Phone phone;

    @ValidQuantity
    private String qty;

    public CartItemInfo() {
    }

    public CartItemInfo(Phone phone, String qty) {
        this.phone = phone;
        this.qty = qty;
    }


    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "CartItemInfo{" +
                "phone=" + phone +
                ", qty='" + qty + '\'' +
                '}';
    }
}
