package com.es.core.model.stock;

import com.es.core.order.OutOfStockException;

import java.util.Optional;

public interface StockService {
    boolean isNotEnoughStock(Long phoneId, Long newQuantity);
    void assertStock(Long phoneId, Long newQuantity) throws OutOfStockException;
    Optional<Stock> getStock(Long phoneId);
    void addStock(Stock stock);
    void updateStock(Stock stock);
    void decreaseStock(Long phoneId, Long quantity);
}
