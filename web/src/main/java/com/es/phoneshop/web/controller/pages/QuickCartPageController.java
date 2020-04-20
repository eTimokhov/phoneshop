package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItemInfo;
import com.es.core.cart.CartService;
import com.es.core.model.ItemNotFoundException;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.request.QuickCartInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping(value = "/quickCart")
public class QuickCartPageController {
    private static final String OUT_OF_STOCK = "outOfStock";
    private static final String ITEM_NOT_FOUND = "itemNotFound";
    @Resource
    private CartService cartService;

    @ModelAttribute("quickCartInfo")
    public QuickCartInfo addCartItemInfos() {
        return new QuickCartInfo(10);
    }

    @ModelAttribute("successMessages")
    public List<String> successMessages() {
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getQuickCart(Model model) {
        return "quickCart";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void updateCart(@Valid @ModelAttribute("quickCartInfo") QuickCartInfo quickCartInfo, BindingResult bindingResult, @ModelAttribute("successMessages") List<String> successMessages, Model model) {
        successMessages.clear();
        List<CartItemInfo> items = quickCartInfo.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (bindingResult.hasFieldErrors("items[" + i + "].phoneId") || bindingResult.hasFieldErrors("items[" + i + "].quantity")) {
                continue;
            }
            try {
                CartItemInfo cartItemInfo = items.get(i);
                cartService.addPhone(cartItemInfo.getPhoneId(), cartItemInfo.getQuantity());
                successMessages.add("Product " + cartItemInfo.getPhoneId() + " added successfully.");
                cartItemInfo.setPhoneId(null);
                cartItemInfo.setQuantity(null);
            } catch (OutOfStockException e) {
                bindingResult.rejectValue("items[" + i + "].quantity", OUT_OF_STOCK);
            } catch (ItemNotFoundException e) {
                bindingResult.rejectValue("items[" + i + "].phoneId", ITEM_NOT_FOUND);
            }
        }
    }

}
