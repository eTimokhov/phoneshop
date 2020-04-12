package com.es.core.cart;

import com.es.core.model.ItemNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneService;
import com.es.core.model.stock.StockService;
import com.es.core.order.OutOfStockException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class HttpSessionCartService implements CartService {

    private static final Logger logger = Logger.getLogger(HttpSessionCartService.class);
    private static final String ERR_OUT_OF_STOCK = "outOfStock";
    private static final String NOT_FOUND = "notFound";

    @Resource
    private Cart cart;

    @Resource
    private PhoneService phoneService;

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
    public void update(CartItem cartItem) throws OutOfStockException {
        updatePhoneInCart(cartItem.getPhone().getId(), cartItem.getQuantity());
        cartCalculationService.recalculateTotalPrice(cart);
    }

    @Override
    public void remove(Long phoneId) throws OutOfStockException {
        updatePhoneInCart(phoneId, 0L);
        cartCalculationService.recalculateTotalPrice(cart);
    }

    @Override
    public void clearCart() {
        getCart().getCartItems().clear();
        cartCalculationService.recalculateTotalPrice(cart);
    }

    @Override
    public boolean removeOutOfStockCartItems() {
        int oldItemsCount = getCart().getCartItems().size();
        getCart().getCartItems().removeIf(
                ci -> stockService.isNotEnoughStock(ci.getPhone().getId(), ci.getQuantity())
        );
        cartCalculationService.recalculateTotalPrice(cart);
        return oldItemsCount != getCart().getCartItems().size();
    }

    private void addPhoneToCart(Long phoneId, Long quantity) throws OutOfStockException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(cartItemMatchPredicate(phoneId))
                .findAny();

        if (cartItemOpt.isPresent()) {
            updateCartItem(cartItemOpt.get(), cartItemOpt.get().getQuantity() + quantity);
        } else {
            createCartItem(phoneId, quantity);
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
            throw new IllegalStateException("Lost update attempt");
        }
    }

    private void createCartItem(Long phoneId, Long newQuantity) throws OutOfStockException {
        Phone phone = phoneService.getPhone(phoneId).orElseThrow(() -> new ItemNotFoundException("Phone with id " + phoneId + " not found."));
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
