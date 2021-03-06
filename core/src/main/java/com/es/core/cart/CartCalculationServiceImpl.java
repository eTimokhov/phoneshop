package com.es.core.cart;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartCalculationServiceImpl implements CartCalculationService {

    @Override
    public void recalculateTotalPrice(Cart cart) {
        BigDecimal subtotal = cart.getCartItems().stream()
                .map(ci -> ci.getPhone().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        cart.setSubtotal(subtotal);
        cart.setTotalPrice(subtotal.add(cart.getDeliveryPrice()));
    }
}
