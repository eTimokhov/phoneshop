package com.es.phoneshop.web.request;

import com.es.core.cart.CartItemInfo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class QuickCartInfo {
    @Valid
    private List<CartItemInfo> items;

    public QuickCartInfo() {
    }

    public QuickCartInfo(int itemsCount) {
        items = new ArrayList<>();
        for (int i = 0; i < itemsCount; i++) {
            items.add(new CartItemInfo());
        }
    }

    public List<CartItemInfo> getItems() {
        return items;
    }

    public void setItems(List<CartItemInfo> items) {
        this.items = items;
    }
}
