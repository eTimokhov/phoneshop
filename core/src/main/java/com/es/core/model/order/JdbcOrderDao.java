package com.es.core.model.order;

import com.es.core.model.ItemNotFoundException;
import com.es.core.model.phone.PhoneDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Transactional
public class JdbcOrderDao implements OrderDao {

    private final static String UPDATE_ORDER =
            "UPDATE orders SET " +
                    "firstName = ?, lastName = ?, deliveryAddress = ?, contactPhoneNo = ?, additionalInfo = ?, " +
                    "deliveryPrice = ?, subtotal = ?, totalPrice = ?, status = ?, placementDate = ? " +
                    "WHERE id = ?";

    private static final String FIND_ORDER_BY_ID =
            "SELECT * FROM orders WHERE id = ?";

    private static final String FIND_ORDER_ITEMS_BY_ORDER_ID =
            "SELECT * FROM orderItems WHERE orderId = ?";

    private static final String SAVE_ORDER_ITEM =
            "INSERT INTO orderItems (orderId, phoneId, quantity) VALUES (?, ?, ?)";

    private static final String FIND_ORDERS =
            "SELECT * FROM orders";

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private BeanPropertyRowMapper<Order> orderBeanPropertyRowMapper;

    @Resource
    private PhoneDao phoneDao;

    private SimpleJdbcInsert insertOrder;

    @PostConstruct
    public void init() {
        insertOrder = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = jdbcTemplate.query(FIND_ORDERS, orderBeanPropertyRowMapper);
        orders.forEach(o -> o.setOrderItems(findOrderItems(o)));
        return orders;
    }

    @Override
    public Optional<Order> get(Long id) {
        try {
            Order order = jdbcTemplate.queryForObject(FIND_ORDER_BY_ID, orderBeanPropertyRowMapper, id);
            order.setOrderItems(findOrderItems(order));
            return Optional.of(order);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private List<OrderItem> findOrderItems(Order order) {
        return jdbcTemplate.query(FIND_ORDER_ITEMS_BY_ORDER_ID, (rs, rowNum) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setPhone(phoneDao.get(rs.getLong("phoneId")).orElseThrow(ItemNotFoundException::new));
            orderItem.setQuantity(rs.getLong("quantity"));
            return orderItem;
        }, order.getId());
    }

    @Override
    public void save(Order order) {
        Objects.requireNonNull(order);
        if (order.getId() != null) {
            update(order);
        } else {
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(order);
            Long orderId = insertOrder.executeAndReturnKey(parameters).longValue();
            order.setId(orderId);
            saveOrderItems(order);
        }
    }

    private void saveOrderItems(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(oi -> jdbcTemplate.update(SAVE_ORDER_ITEM, order.getId(), oi.getPhone().getId(), oi.getQuantity()));
    }


    private void update(Order order) {
        jdbcTemplate.update(UPDATE_ORDER,
                order.getFirstName(), order.getLastName(), order.getDeliveryAddress(),
                order.getContactPhoneNo(), order.getAdditionalInfo(), order.getDeliveryPrice(),
                order.getSubtotal(), order.getTotalPrice(), order.getStatus().toString(), order.getPlacementDate(),
                order.getId());
    }
}
