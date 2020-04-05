package com.es.phoneshop.web.request;

import com.es.core.cart.CartItemInfo;

import javax.validation.Valid;
import java.util.List;

public class CartItemInfoListWrapper {
    @Valid
    private List<CartItemInfo> items;

    public List<CartItemInfo> getItems() {
        return items;
    }

    public void setItems(List<CartItemInfo> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CartItemInfoListWrapper{" +
                "items=" + items +
                '}';
    }
}
