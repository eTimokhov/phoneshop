package com.es.core.order;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderDao orderDao;

    @Resource
    CartService cartService;

    @Resource
    StockService stockService;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Override
    public Optional<Order> get(Long orderId) {
        return orderDao.get(orderId);
    }

    @Override
    public Order createOrder() {
        if (cartService.getCart().getCartItems().isEmpty()) {
            throw new IllegalArgumentException("Cannot create order for empty cart.");
        }

        Order order = new Order();
        updateOrderItems(order);
        return order;
    }

    private void updateOrderItems(Order order) {
        order.setOrderItems(createOrderItems(cartService.getCart().getCartItems()));

        BigDecimal subtotal = cartService.getCart().getTotalPrice();
        order.setSubtotal(subtotal);
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(subtotal.add(deliveryPrice));
    }

    private List<OrderItem> createOrderItems(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(ci -> new OrderItem(ci.getPhone(), ci.getQuantity()))
                .collect(Collectors.toList());
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
        boolean anyItemsWereRemoved = cartService.removeOutOfStockCartItems();
        if (!anyItemsWereRemoved) {
            order.getOrderItems().forEach(oi -> stockService.decreaseStock(oi.getPhone().getId(), oi.getQuantity()));
            order.setStatus(OrderStatus.NEW);
            orderDao.save(order);
        } else {
            updateOrderItems(order);
            throw new OutOfStockException();
        }
    }
}
