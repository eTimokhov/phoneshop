package com.es.core.model.stock;

import com.es.core.model.ItemNotFoundException;
import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockDao stockDao;

    @Override
    public boolean isEnoughStock(Long phoneId, Long newQuantity) {
        Stock stock = stockDao.getStock(phoneId).orElseThrow(() -> new ItemNotFoundException("Stock with id " + phoneId + " not found."));
        return stock.getStock() >= newQuantity;
    }

    @Override
    public void assertStock(Long phoneId, Long newQuantity) throws OutOfStockException {
        if (!isEnoughStock(phoneId, newQuantity)) {
            throw new OutOfStockException();
        }
    }

    public Optional<Stock> getStock(Long phoneId) {
        return stockDao.getStock(phoneId);
    }

    public void addStock(Stock stock) {
        stockDao.insert(stock);
    }

    public void updateStock(Stock stock) {
        stockDao.update(stock);
    }
}
