package com.es.phoneshop.web.controller.pages;

import com.es.core.model.ItemNotFoundException;
import com.es.core.model.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class ProductDetailsPageController {

    @Resource
    PhoneService phoneService;

    @RequestMapping(value = "/productDetails/{id}", method = RequestMethod.GET)
    public String showProductDetails(@PathVariable long id, Model model) throws ItemNotFoundException {
        model.addAttribute(phoneService.getPhone(id).orElseThrow(ItemNotFoundException::new));
        return "productDetails";
    }
}
