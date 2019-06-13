package com.es.core.cart;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;
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

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) throws OutOfStockException {
        Phone phone = phoneDao.get(phoneId).orElseThrow(() -> new IllegalArgumentException("Phone with id " + phoneId + " not found."));

        Stock stock = stockDao.getStock(phoneId);
        if (stock.getStock() - stock.getReserved() < quantity) {
            throw new OutOfStockException();
        }
        stock.setReserved(stock.getReserved() + quantity);
        stockDao.update(stock);

        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getPhone().getId().equals(phoneId))
                .findAny();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cart.getCartItems().add(new CartItem(phone, quantity));
        }
    }

    @Override
    public void update(Map<Long, Long> items) throws OutOfStockException {
        for (Map.Entry<Long, Long> entry : items.entrySet()) {
            Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                    .filter(ci -> ci.getPhone().getId().equals(entry.getKey()))
                    .findAny();
            if (optionalCartItem.isPresent()) {
                optionalCartItem.get().setQuantity(entry.getValue());
            } else {
                addPhone(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void remove(Long phoneId) {
        cart.getCartItems().removeIf(ci -> ci.getPhone().getId().equals(phoneId));
    }

}
