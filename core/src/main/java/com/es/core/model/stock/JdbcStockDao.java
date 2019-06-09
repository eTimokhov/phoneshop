package com.es.core.model.stock;

import com.es.core.model.phone.PhoneDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class JdbcStockDao implements StockDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private BeanPropertyRowMapper<Stock> stockBeanPropertyRowMapper;
    @Resource
    private PhoneDao phoneDao;

    private static final String FIND_STOCK_BY_PHONE_ID = "SELECT * FROM stocks WHERE phoneId = ?";
    private static final String UPDATE_STOCK = "UPDATE stocks SET stock = ?, reserved = ? WHERE phoneId = ?";
    private static final String INSERT_STOCK = "INSERT INTO stocks (phoneId, stock, reserved) VALUES (?, ?, ?);";

    @Override
    public Stock getStock(Long phoneId) {
        Stock stock = jdbcTemplate.queryForObject(FIND_STOCK_BY_PHONE_ID, stockBeanPropertyRowMapper, phoneId);
        stock.setPhone(phoneDao.get(phoneId).orElseThrow(() -> new IllegalArgumentException("Phone with id " + phoneId + " not found.")));
        return stock;
    }

    @Override
    public void insert(Stock stock) {
        Objects.requireNonNull(stock);
        jdbcTemplate.update(INSERT_STOCK, stock.getPhone().getId(), stock.getStock(), stock.getReserved());
    }

    @Override
    public void update(Stock stock) {
        Objects.requireNonNull(stock);
        jdbcTemplate.update(UPDATE_STOCK, stock.getStock(), stock.getReserved(), stock.getPhone().getId());
    }

}
