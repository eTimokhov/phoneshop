package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.SortingDirection;
import com.es.core.pages.product.PaginationData;
import com.es.core.pages.product.ProductPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {

    @Resource
    ProductPageService productPageService;

    private static final int RESULTS_PER_PAGE = 10;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(value = "searchTerms", required = false) String searchTerms,
                                  @RequestParam(value = "orderBy", defaultValue = "model") String orderBy,
                                  @RequestParam(value = "sortDirection", required = false) SortingDirection sortingDirection,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  Model model) {
        if (sortingDirection == null) {
            sortingDirection = SortingDirection.ASCENDING;
        }

        PaginationData paginationData = productPageService.getPaginationData(searchTerms, orderBy, sortingDirection, page, RESULTS_PER_PAGE);
        model.addAttribute("paginationData", paginationData);
        return "productList";
    }
}
