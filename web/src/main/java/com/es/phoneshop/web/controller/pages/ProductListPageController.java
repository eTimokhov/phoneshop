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

    private static final int RESULTS_PER_PAGE = 10;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(@RequestParam(value = "searchTerms", required = false) String searchTerms,
                                  @RequestParam(value = "orderBy", defaultValue = "model") String orderBy,
                                  @RequestParam(value = "asc", defaultValue = "true") boolean ascending,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  Model model) {
        List<Phone> phones = phoneDao.findAll(searchTerms, orderBy, ascending, (page - 1) * RESULTS_PER_PAGE, RESULTS_PER_PAGE);
        Integer totalCount = phoneDao.findTotalCount(searchTerms);
        model.addAttribute("phones", phones);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("resultsPerPage", RESULTS_PER_PAGE);
        return "productList";
    }
}
