package com.es.core.pages.product;

import com.es.core.model.phone.SortingDirection;

public interface ProductPageService {
    PaginationData getPaginationData(String searchTerms, String orderBy, SortingDirection sortingDirection, int page, int resultsPerPage);
}
