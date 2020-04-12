package com.es.core.model.stock;

import com.es.core.order.OutOfStockException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Resource
    private StockDao stockDao;

    @Override
    public boolean isNotEnoughStock(Long phoneId, Long newQuantity) {
        return stockDao.getStock(phoneId).map(stock -> stock.getStock() < newQuantity).orElse(true);

    }

    @Override
    public void assertStock(Long phoneId, Long newQuantity) throws OutOfStockException {
        if (isNotEnoughStock(phoneId, newQuantity)) {
            throw new OutOfStockException();
        }
    }

    @Override
    public Optional<Stock> getStock(Long phoneId) {
        return stockDao.getStock(phoneId);
    }

    @Override
    public void addStock(Stock stock) {
        stockDao.insert(stock);
    }

    @Override
    public void updateStock(Stock stock) {
        stockDao.update(stock);
    }

    @Override
    public void decreaseStock(Long phoneId, Long quantity) {
        Stock stock = getStock(phoneId).orElseThrow(IllegalArgumentException::new);
        stock.setStock(stock.getStock() - quantity);
        updateStock(stock);
    }
}
