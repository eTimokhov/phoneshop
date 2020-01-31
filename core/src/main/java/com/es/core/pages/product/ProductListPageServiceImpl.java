package com.es.core.pages.product;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.phone.SortingDirection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductListPageServiceImpl implements ProductListPageService {

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> findPage(String searchTerms, String orderBy, SortingDirection sortingDirection, int page, int resultsPerPage) {
        return phoneDao.findAll(searchTerms, orderBy, sortingDirection, (page - 1) * resultsPerPage, resultsPerPage);
    }

    @Override
    public PaginationData getPaginationData(String searchTerms, int resultsPerPage) {
        int totalCount = phoneDao.findTotalCount(searchTerms);
        return new PaginationData(totalCount, resultsPerPage);

    }
}
