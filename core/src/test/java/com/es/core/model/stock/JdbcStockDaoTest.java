package com.es.core.model.stock;

import com.es.core.IntegrationTest;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class JdbcStockDaoTest extends IntegrationTest {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private StockDao jdbcStockDao;

    @Test
    public void testGetCorrectStock() {
        Phone phone1 = phoneDao.get(1003L).get();
        Stock stock1 = new Stock();
        stock1.setPhone(phone1);
        stock1.setStock(13L);
        stock1.setReserved(2L);
        assertEquals(stock1, jdbcStockDao.getStock(1003L));

        Phone phone2 = phoneDao.get(1121L).get();
        Stock stock2 = new Stock();
        stock2.setPhone(phone2);
        stock2.setStock(11L);
        stock2.setReserved(10L);
        assertEquals(stock2, jdbcStockDao.getStock(1121L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStockIncorrectPhone() {
        jdbcStockDao.getStock(999L);
    }

    @Test
    public void testInsert() {
        Phone phone = new Phone();
        phone.setBrand("brand");
        phone.setModel("model");
        phone.setDescription("description");
        phone.setPrice(BigDecimal.valueOf(500.0));

        Stock stock = new Stock();
        stock.setPhone(phone);
        stock.setStock(50L);
        stock.setReserved(10L);

        phoneDao.save(phone);
        jdbcStockDao.insert(stock);

        long phoneId = phone.getId();

        assertEquals(stock, jdbcStockDao.getStock(phoneId));
    }

    @Test(expected = NullPointerException.class)
    public void testInsertNull() {
        jdbcStockDao.insert(null);
    }

    @Test
    public void testUpdate() {
        Stock stock = jdbcStockDao.getStock(1003L);
        stock.setStock(50L);
        stock.setReserved(10L);

        jdbcStockDao.update(stock);

        Stock updatedStock = jdbcStockDao.getStock(1003L);
        assertEquals(updatedStock, stock);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNull() {
        jdbcStockDao.update(null);
    }
}
