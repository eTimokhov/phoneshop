package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.order.OutOfStockException;
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

@Controller
@ControllerAdvice
@RequestMapping(value = "/cart")
public class CartPageController {
    private static final String OUT_OF_STOCK = "outOfStock";
    @Resource
    private CartService cartService;

    @ModelAttribute("cartItemsWrapper")
    public CartItemInfoListWrapper addCartItemInfos() {
        CartItemInfoListWrapper listWrapper = new CartItemInfoListWrapper();
        listWrapper.setItems(cartService.getCart().getCartItems());
        return listWrapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart(@Valid @ModelAttribute("cartItemsWrapper") CartItemInfoListWrapper cartItemsWrapper, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return;
        }
        List<CartItem> items = cartItemsWrapper.getItems();
        for (int i = 0; i < items.size(); i++) {
            try {
                cartService.update(items.get(i));
            } catch (OutOfStockException e) {
                bindingResult.rejectValue("items[" + i + "].quantity", OUT_OF_STOCK);
            }
        }
        updateCartItemsModel(cartItemsWrapper);
    }

    private void updateCartItemsModel(CartItemInfoListWrapper cartItemsWrapper) {
        Cart cart = cartService.getCart();
        cartItemsWrapper.getItems()
                .removeIf(cii -> cart.getCartItems().stream()
                        .noneMatch(ci -> ci.getPhone().equals(cii.getPhone())));
    }
}
