package com.es.phoneshop.web.controller;

import com.es.core.cart.Cart;
import com.es.core.cart.CartService;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.response.AddToCartResponse;
import com.es.phoneshop.web.response.GetCartInfoResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public AddToCartResponse addPhone(@RequestParam Long phoneId,
                                      @RequestParam Long quantity) {
        try {
            cartService.addPhone(phoneId, quantity);
        } catch (OutOfStockException e) {
            return new AddToCartResponse(false, phoneId,"Not enough stock");
        }
        return new AddToCartResponse(true);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public GetCartInfoResponse getCartInfo(Long phoneId, Long quantity) {
        return new GetCartInfoResponse(cartService.getTotalCount(), cartService.getCart().getTotalPrice());
    }


}
