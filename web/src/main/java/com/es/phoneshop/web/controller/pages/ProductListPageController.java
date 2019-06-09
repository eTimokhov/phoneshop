package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.Phone;
import com.es.core.model.stock.StockDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(value = "searchQuery", required = false) String searchQuery,
                                  @RequestParam(value = "orderBy", defaultValue = "model") String orderBy,
                                  @RequestParam(value = "asc", defaultValue = "true") boolean ascending,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  Model model) {
        List<Phone> phones = phoneDao.findAll(searchQuery, orderBy, ascending, (page - 1) * 10, 10);
        model.addAttribute("phones", phones);
        return "productList";
    }
}
