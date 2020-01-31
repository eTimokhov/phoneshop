package com.es.core.model.stock;

import org.springframework.stereotype.Component;

@Component
public class StockManager {

    public Long getAvailable(Stock stock) {
        return stock.getStock() - stock.getReserved();
    }

    public void changeReservedBy(Stock stock, Long quantity) {
        stock.setReserved(stock.getReserved() + quantity);
    }

}
