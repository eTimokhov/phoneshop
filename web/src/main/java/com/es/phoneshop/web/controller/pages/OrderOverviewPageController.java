package com.es.phoneshop.web.controller.pages;

import com.es.core.model.ItemNotFoundException;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @Resource
    OrderService orderService;

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public String get(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.get(orderId).orElseThrow(ItemNotFoundException::new));
        return "orderOverview";
    }
}
