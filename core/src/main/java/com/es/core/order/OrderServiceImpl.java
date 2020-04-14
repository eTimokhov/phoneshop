package com.es.core.order;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.model.ItemNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderDao;
import com.es.core.model.order.OrderForm;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderDao orderDao;

    @Resource
    CartService cartService;

    @Resource
    StockService stockService;

    @Override
    public List<Order> getOrders() {
        return orderDao.getOrders();
    }

    @Override
    public Order get(Long orderId) {
        return orderDao.get(orderId).orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public Order createOrder(OrderForm orderForm) {
        if (!canCreateOrder()) {
            throw new IllegalStateException("Cannot create order.");
        }
        Order order = new Order();
        order.setFirstName(orderForm.getFirstName());
        order.setLastName(orderForm.getLastName());
        order.setDeliveryAddress(orderForm.getDeliveryAddress());
        order.setContactPhoneNo(orderForm.getContactPhoneNo());
        order.setAdditionalInfo(order.getAdditionalInfo());

        updateOrderItems(order);
        return order;
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.save(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Order order = get(orderId);
        order.setStatus(orderStatus);
        updateOrder(order);
    }

    private boolean canCreateOrder() {
        return !cartService.getCart().getCartItems().isEmpty()
                && cartService.getCart().getTotalPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    private void updateOrderItems(Order order) {
        order.setOrderItems(createOrderItems(cartService.getCart().getCartItems()));
        order.setSubtotal(cartService.getCart().getSubtotal());
        order.setDeliveryPrice(cartService.getCart().getDeliveryPrice());
        order.setTotalPrice(cartService.getCart().getTotalPrice());
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
            order.setPlacementDate(new Date());
            orderDao.save(order);
        } else {
            updateOrderItems(order);
            throw new OutOfStockException();
        }
    }
}
