package com.es.core.pages.product;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortingDirection;

import java.util.List;

public interface ProductListPageService {
    PaginationData getPaginationData(String searchTerms, String orderBy, SortingDirection sortingDirection, int page, int resultsPerPage);
}
