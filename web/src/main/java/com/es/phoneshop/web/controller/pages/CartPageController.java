package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemInfo;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.request.CartItemInfoListWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ControllerAdvice
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @ModelAttribute("cartItemInfos")
    public CartItemInfoListWrapper addCartItemInfos() {
        List<CartItemInfo> cartItemInfos = cartService.getCart().getCartItems().stream()
                .map(ci -> new CartItemInfo(ci.getPhone(), ci.getQuantity().toString()))
                .collect(Collectors.toList());
        CartItemInfoListWrapper listWrapper = new CartItemInfoListWrapper();
        listWrapper.setItems(cartItemInfos);
        return listWrapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart(@Valid @ModelAttribute("cartItemInfos") CartItemInfoListWrapper cartItemInfos, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return;
        }
        List<CartItem> cartItems = cartItemInfos.getItems().stream()
                .map(cii -> new CartItem(cii.getPhone(), Long.parseLong(cii.getQty())))
                .collect(Collectors.toList());

        List<String> errors = cartService.update(cartItems);
        for (int i = 0; i < errors.size(); i++) {
            if (errors.get(i) != null) {
                bindingResult.rejectValue("items[" + i + "].qty", errors.get(i));
            }
        }

        updateCartItemInfos(cartItemInfos);
    }

    private void updateCartItemInfos(@ModelAttribute("cartItemInfos") @Valid CartItemInfoListWrapper cartItemInfos) {
        Cart cart = cartService.getCart();
        cartItemInfos.getItems()
                .removeIf(cii -> cart.getCartItems().stream()
                        .noneMatch(ci -> ci.getPhone().equals(cii.getPhone())));
    }
}
