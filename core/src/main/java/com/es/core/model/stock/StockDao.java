package com.es.core.model.stock;

public interface StockDao {
    Stock getStock(Long phoneId);
    void insert(Stock stock);
    void update(Stock stock);
}
