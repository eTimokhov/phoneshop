package com.es.core.cart;

import com.es.core.model.ItemNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.StockService;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private StockService stockService;

    @Resource
    private CartCalculationService cartCalculationService;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public Long getTotalProductsQuantity() {
        return cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        addPhoneToCart(phoneId, quantity);
        cartCalculationService.recalculateTotalPrice(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> entry : items.entrySet()) {
            try {
                updatePhoneInCart(entry.getKey(), entry.getValue());
            } catch (ItemNotFoundException | OutOfStockException e) {
                e.printStackTrace();
            }
        }
        cartCalculationService.recalculateTotalPrice(cart);
    }

    @Override
    public void remove(Long phoneId) throws OutOfStockException {
        updatePhoneInCart(phoneId, 0L);
        cartCalculationService.recalculateTotalPrice(cart);
    }

    private void addPhoneToCart(Long phoneId, Long quantity) throws OutOfStockException {
        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(cartItemMatchPredicate(phoneId))
                .findAny();
        if (quantity > 0) {
            if (cartItemOpt.isPresent()) {
                updateCartItem(cartItemOpt.get(), cartItemOpt.get().getQuantity() + quantity);
            } else {
                createCartItem(phoneId, quantity);
            }
        }
    }

    private void updatePhoneInCart(Long phoneId, Long newQuantity) throws OutOfStockException {
        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(cartItemMatchPredicate(phoneId))
                .findAny();

        if (newQuantity == 0) {
            removeCartItem(phoneId);
        } else if (cartItemOpt.isPresent()) {
            updateCartItem(cartItemOpt.get(), newQuantity);
        } else {
            createCartItem(phoneId, newQuantity);
        }
    }

    private void createCartItem(Long phoneId, Long newQuantity) throws OutOfStockException {
        Phone phone = phoneDao.get(phoneId).orElseThrow(() -> new ItemNotFoundException("Phone with id " + phoneId + " not found."));
        stockService.assertStock(phoneId, newQuantity);
        cart.getCartItems().add(new CartItem(phone, newQuantity));
    }

    private void updateCartItem(CartItem cartItem, Long newQuantity) throws OutOfStockException {
        stockService.assertStock(cartItem.getPhone().getId(), newQuantity);
        cartItem.setQuantity(newQuantity);
    }

    private void removeCartItem(Long phoneId) {
        cart.getCartItems().removeIf(cartItemMatchPredicate(phoneId));
    }

    private Predicate<CartItem> cartItemMatchPredicate(Long phoneId) {
        return ci -> ci.getPhone().getId().equals(phoneId);
    }

}
