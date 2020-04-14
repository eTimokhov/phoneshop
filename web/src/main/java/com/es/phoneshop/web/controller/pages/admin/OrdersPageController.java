package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.ItemNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "admin/orders";
    }

    @RequestMapping(path = "/{orderId}", method = RequestMethod.GET)
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.get(orderId).orElseThrow(ItemNotFoundException::new));
        return "admin/orderInfo";
    }

    @RequestMapping(path = "/{orderId}", method = RequestMethod.POST)
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam OrderStatus orderStatus) {
        Order order = orderService.get(orderId).orElseThrow(ItemNotFoundException::new);
        order.setStatus(orderStatus);
        orderService.updateOrder(order);
        return "redirect:/admin/orders/" + orderId;
    }
}
