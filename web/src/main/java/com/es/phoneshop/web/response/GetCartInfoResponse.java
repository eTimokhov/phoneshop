package com.es.phoneshop.web.response;

import java.math.BigDecimal;

public class GetCartInfoResponse {
    private Long totalCount;
    private BigDecimal totalPrice;

    public GetCartInfoResponse() {
    }

    public GetCartInfoResponse(Long totalCount, BigDecimal totalPrice) {
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
