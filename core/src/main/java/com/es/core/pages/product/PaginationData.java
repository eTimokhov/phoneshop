package com.es.core.pages.product;

import com.es.core.model.phone.Phone;

import java.util.List;

public class PaginationData {
    private List<Phone> phones;
    private int totalCount;
    private int resultsPerPage;

    public PaginationData() {
    }

    public PaginationData(List<Phone> phones, int totalCount, int resultPerPage) {
        this.phones = phones;
        this.totalCount = totalCount;
        this.resultsPerPage = resultPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

}
