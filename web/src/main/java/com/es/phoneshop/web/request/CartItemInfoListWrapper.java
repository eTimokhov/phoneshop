package com.es.phoneshop.web.request;

import com.es.core.cart.CartItem;

import javax.validation.Valid;
import java.util.List;

public class CartItemInfoListWrapper {
    @Valid
    private List<CartItem> items;

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CartItemInfoListWrapper{" +
                "items=" + items +
                '}';
    }
}
