package com.es.core.model.stock;

import com.es.core.order.OutOfStockException;

import java.util.Optional;

public interface StockService {
    boolean isEnoughStock(Long phoneId, Long newQuantity);
    void assertStock(Long phoneId, Long newQuantity) throws OutOfStockException;
    Optional<Stock> getStock(Long phoneId);
    void insert(Stock stock);
    void update(Stock stock);
}
