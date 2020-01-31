package com.es.core.cart;

import com.es.core.model.ItemNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;
import com.es.core.model.stock.StockManager;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private StockDao stockDao;

    @Resource
    private StockManager stockManager;

    @Resource
    private CartCalculationService cartCalculationService;

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public Long getTotalCount() {
        return cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException, ItemNotFoundException {
        addOrUpdate(phoneId, quantity, true);
        cartCalculationService.recalculateTotalPrice(cart);
    }

    @Override
    public void update(Map<Long, Long> items) {
        for (Map.Entry<Long, Long> entry : items.entrySet()) {
            try {
                addOrUpdate(entry.getKey(), entry.getValue(), false);
            } catch (ItemNotFoundException | OutOfStockException e) {
                e.printStackTrace();
            }
        }
        cartCalculationService.recalculateTotalPrice(cart);
    }

    @Override
    public void remove(Long phoneId) throws OutOfStockException, ItemNotFoundException {
        addOrUpdate(phoneId, 0L,false);
        cartCalculationService.recalculateTotalPrice(cart);
    }

    private void addOrUpdate(Long phoneId, Long quantity, boolean addQuantity) throws ItemNotFoundException, OutOfStockException {
        Phone phone = phoneDao.get(phoneId).orElseThrow(() -> new ItemNotFoundException("Phone with id " + phoneId + " not found."));
        Stock stock = stockDao.getStock(phoneId).orElseThrow(() -> new ItemNotFoundException("Stock with id " + phoneId + " not found."));

        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getPhone().getId().equals(phoneId))
                .findAny();

        Long newQuantity = quantity;
        Long quantityDifference = quantity;
        if (optionalCartItem.isPresent()) {
            if (addQuantity) {
                newQuantity += optionalCartItem.get().getQuantity();
            } else {
                quantityDifference = quantity - optionalCartItem.get().getQuantity();
            }
        }

        if (stockManager.getAvailable(stock) < quantityDifference) {
            throw new OutOfStockException();
        }
        stockManager.changeReservedBy(stock, quantityDifference);
        stockDao.update(stock);

        if (newQuantity == 0) {
            removePhoneFromCart(phone);
        } else if (optionalCartItem.isPresent()) {
            updateCartItem(optionalCartItem.get(), newQuantity);
        } else {
            addPhoneToCart(phone, newQuantity);
        }
    }

    private void addPhoneToCart(Phone phone, Long quantity) {
        cart.getCartItems().add(new CartItem(phone, quantity));

    }

    private void updateCartItem(CartItem cartItem, Long newQuantity) {
        cartItem.setQuantity(newQuantity);
    }

    private void removePhoneFromCart(Phone phone) {
        cart.getCartItems().removeIf(ci -> ci.getPhone().getId().equals(phone.getId()));
    }

}
