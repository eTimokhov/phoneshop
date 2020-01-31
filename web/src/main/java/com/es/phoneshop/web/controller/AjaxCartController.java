package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.core.model.ItemNotFoundException;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.request.AddToCartRequest;
import com.es.phoneshop.web.response.AddToCartResponse;
import com.es.phoneshop.web.response.GetCartInfoResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@Validated
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;
    //NotNull Min typeMismatch OutOfStockException
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public AddToCartResponse addPhone(@Valid AddToCartRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                        .findFirst()
                        .map(FieldError::getCode)
                        .orElse("Invalid request");
            return new AddToCartResponse(false, request.getPhoneId(), errorMessage);
        }
        try {
            cartService.addPhone(request.getPhoneId(), request.getQuantity());
        } catch (OutOfStockException | ItemNotFoundException e) {
            return new AddToCartResponse(false, request.getPhoneId(), e.getClass().getSimpleName());
        }
        return new AddToCartResponse(true);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public GetCartInfoResponse getCartInfo() {
        return new GetCartInfoResponse(cartService.getTotalCount(), cartService.getCart().getTotalPrice());
    }


}
