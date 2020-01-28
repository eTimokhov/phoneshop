package com.es.core.cart;

import com.es.core.IntegrationTest;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;
import com.es.core.order.OutOfStockException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class HttpSessionCartServiceTest extends IntegrationTest {

    @Spy
    private Cart cart;

    @Mock
    private PhoneDao phoneDao;

    @Mock
    private StockDao stockDao;

    @InjectMocks
    @Resource
    private CartService httpSessionCartService;

    @Before
    public void clearCart() {
        httpSessionCartService.getCart().getCartItems().clear();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Phone phone1 = createPhone(1000L, BigDecimal.valueOf(100.0));
        Stock stock1 = createStock(phone1, 20L, 10L);
        when(phoneDao.get(1000L)).thenReturn(Optional.of(phone1));
        when(stockDao.getStock(1000L)).thenReturn(stock1);

        Phone phone2 = createPhone(1001L, BigDecimal.valueOf(250.0));
        Stock stock2 = createStock(phone2, 30L, 15L);
        when(phoneDao.get(1001L)).thenReturn(Optional.of(phone2));
        when(stockDao.getStock(1001L)).thenReturn(stock2);

    }

    @Test
    public void testPriceCalculation() throws OutOfStockException {
        Cart cart = httpSessionCartService.getCart();

        httpSessionCartService.addPhone(1000L, 1L);
        assertEquals(BigDecimal.valueOf(100.0), cart.getTotalPrice());

        httpSessionCartService.addPhone(1001L, 2L);
        assertEquals(BigDecimal.valueOf(600.0), cart.getTotalPrice());
    }

    @Test
    public void testTotalCountCalculation() throws OutOfStockException {
        httpSessionCartService.addPhone(1000L, 2L);
        assertEquals((Long)2L, httpSessionCartService.getTotalCount());

        httpSessionCartService.addPhone(1001L, 2L);
        assertEquals((Long)4L, httpSessionCartService.getTotalCount());

        httpSessionCartService.addPhone(1000L, 1L);
        assertEquals((Long)5L, httpSessionCartService.getTotalCount());

    }

    @Test
    public void testRemoveItems() throws OutOfStockException {
        httpSessionCartService.addPhone(1000L, 2L);
        httpSessionCartService.addPhone(1001L, 3L);
        assertEquals((Long)5L, httpSessionCartService.getTotalCount());

        httpSessionCartService.remove(1000L);
        assertEquals((Long)3L, httpSessionCartService.getTotalCount());

    }

    @Test(expected = OutOfStockException.class)
    public void testOutOfStock() throws OutOfStockException {
        httpSessionCartService.addPhone(1000L, 11L);
    }

    private Phone createPhone(Long phoneId, BigDecimal price) {
        Phone phone = new Phone();
        phone.setBrand("brand");
        phone.setDescription("description");
        phone.setId(phoneId);
        phone.setPrice(price);
        return phone;
    }

    private Stock createStock(Phone phone, Long stockValue, Long reserved) {
        Stock stock = new Stock();
        stock.setPhone(phone);
        stock.setStock(stockValue);
        stock.setReserved(reserved);
        return stock;
    }

}
