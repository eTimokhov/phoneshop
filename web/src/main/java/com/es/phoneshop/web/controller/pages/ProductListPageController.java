package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import com.es.core.model.phone.Phone;
import com.es.core.pages.product.PaginationData;
import com.es.core.pages.product.ProductListPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {

    @Resource
    ProductListPageService productListPageService;

    private static final int RESULTS_PER_PAGE = 10;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(value = "searchTerms", required = false) String searchTerms,
                                  @RequestParam(value = "orderBy", defaultValue = "model") String orderBy,
                                  @RequestParam(value = "asc", defaultValue = "true") boolean ascending,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  Model model) {
        List<Phone> phones = productListPageService.findPage(searchTerms, orderBy, ascending, page, RESULTS_PER_PAGE);

        PaginationData paginationData = productListPageService.getPaginationData(searchTerms, RESULTS_PER_PAGE);
        model.addAttribute("phones", phones);
        model.addAttribute("paginationData", paginationData);
        return "productList";
    }
}
