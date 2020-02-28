package com.es.core.model.stock;

import java.util.Optional;

public interface StockDao {
    Optional<Stock> getStock(Long phoneId);
    void insert(Stock stock);
    void update(Stock stock);
}
