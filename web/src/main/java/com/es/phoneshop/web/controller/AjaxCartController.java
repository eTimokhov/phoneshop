package com.es.phoneshop.web.controller;

import com.es.core.cart.CartDto;
import com.es.core.cart.CartService;
import com.es.core.model.ItemNotFoundException;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.request.AddToCartRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addPhone(@Valid AddToCartRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                        .findFirst()
                        .map(FieldError::getCode)
                        .orElse("Invalid request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try {
            cartService.addPhone(request.getPhoneId(), request.getQuantity());
        } catch (OutOfStockException | ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getClass().getSimpleName());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public CartDto getCart() {
        return new CartDto(cartService.getCart());
    }



}
