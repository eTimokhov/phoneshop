package com.es.core.pages.product;

public class PaginationData {
    private int totalCount;
    private int resultPerPage;

    public PaginationData() {
    }

    public PaginationData(int totalCount, int resultPerPage) {
        this.totalCount = totalCount;
        this.resultPerPage = resultPerPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getResultsPerPage() {
        return resultPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultPerPage = resultsPerPage;
    }
}
