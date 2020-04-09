package com.es.core.pages.product;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.SortingDirection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductPageServiceImpl implements ProductPageService {

    @Resource
    private PhoneDao phoneDao;

    @Override
    public PaginationData getPaginationData(String searchTerms, String orderBy, SortingDirection sortingDirection, int page, int resultsPerPage) {
        List<Phone> phones = phoneDao.findAll(searchTerms, orderBy, sortingDirection, (page - 1) * resultsPerPage, resultsPerPage);
        int totalCount = phoneDao.findTotalCount(searchTerms);
        return new PaginationData(phones, totalCount, resultsPerPage);
    }
}
