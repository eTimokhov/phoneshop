package com.es.core.pages.product;

import com.es.core.model.phone.Phone;

import java.util.List;

public interface ProductListPageService {
    List<Phone> findPage(String searchTerms, String orderBy, boolean ascending, int page, int resultsPerPage);
    PaginationData getPaginationData(String searchTerms, int resultsPerPage);
}
