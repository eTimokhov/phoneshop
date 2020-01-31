package com.es.core.model.stock;

import com.es.core.model.phone.PhoneDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Component
@Transactional
public class JdbcStockDao implements StockDao {
    private static final String FIND_STOCK_BY_PHONE_ID = "SELECT * FROM stocks WHERE phoneId = ?";
    private static final String UPDATE_STOCK = "UPDATE stocks SET stock = ?, reserved = ? WHERE phoneId = ?";
    private static final String INSERT_STOCK = "INSERT INTO stocks (phoneId, stock, reserved) VALUES (?, ?, ?);";

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private BeanPropertyRowMapper<Stock> stockBeanPropertyRowMapper;
    @Resource
    private PhoneDao phoneDao;

    @Override
    public Optional<Stock> getStock(Long phoneId) {
        Stock stock;
        try {
            stock = jdbcTemplate.queryForObject(FIND_STOCK_BY_PHONE_ID, stockBeanPropertyRowMapper, phoneId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        stock.setPhone(phoneDao.get(phoneId).orElseThrow(() -> new IllegalArgumentException("Phone with id " + phoneId + " not found.")));
        return Optional.of(stock);
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
