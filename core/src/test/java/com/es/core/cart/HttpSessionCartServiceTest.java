package com.es.core.cart;

import com.es.core.IntegrationTest;
import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import com.es.core.model.stock.Stock;
import com.es.core.model.stock.StockDao;
import com.es.core.order.OutOfStockException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    @Mock
    private Cart cart = new Cart();

    @Mock
    private PhoneDao phoneDao;

    @Mock
    private StockDao stockDao;

    @InjectMocks
    private CartService httpSessionCartService = new HttpSessionCartService();

    private List<CartItem> cartItems = new ArrayList<>();

    @Before
    public void clearCart() {
        httpSessionCartService.getCart().getCartItems().clear();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
       // when(cart.getCartItems()).thenReturn(cartItems);

        Phone phone1 = createPhone(1000L, BigDecimal.valueOf(100.0));
        Stock stock1 = createStock(phone1, 20L, 10L);
        when(phoneDao.get(1000L)).thenReturn(Optional.of(phone1));
        when(stockDao.getStock(1000L)).thenReturn(stock1);

        Phone phone2 = createPhone(1001L, BigDecimal.valueOf(250.0));
        Stock stock2 = createStock(phone2, 10L, 8L);
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
