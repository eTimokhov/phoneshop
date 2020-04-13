package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.request.OrderForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    @ModelAttribute("orderForm")
    public OrderForm addOrderForm() {
        return new OrderForm();
    }

    @ModelAttribute("cart")
    public Cart addCart() {
        return cartService.getCart();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder() {
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@ModelAttribute("orderForm") @Valid OrderForm orderForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "order";
        }
        Order order = orderService.createOrder();
        order.setFirstName(orderForm.getFirstName());
        order.setLastName(orderForm.getLastName());
        order.setDeliveryAddress(orderForm.getDeliveryAddress());
        order.setContactPhoneNo(orderForm.getContactPhoneNo());
        order.setAdditionalInfo(order.getAdditionalInfo());

        try {
            orderService.placeOrder(order);
            cartService.clearCart();
            return "redirect:/orderOverview/" + order.getId();
        } catch (OutOfStockException e) {
            model.addAttribute("warningMessage", "Some products are out stock. They were removed from your cart.");
            return "order";
        }

    }
}
