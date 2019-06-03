package com.es.core.model.phone;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertPhone;

    @PostConstruct
    public void init() {
        insertPhone = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");
    }

    private static final String FIND_COLORS_BY_PHONE_ID =
            "SELECT * FROM phone2color " +
                    "JOIN phones ON phones.id = phone2color.phoneId " +
                    "JOIN colors ON colors.id = phone2color.colorId " +
                    "WHERE phones.id = ?";

    private static final String FIND_ALL_PHONES =
            "SELECT * FROM phones OFFSET ? LIMIT ?";

    private static final String FIND_PHONE_BY_ID =
            "SELECT * FROM phones WHERE id = ?";

    private static final String SAVE_PHONE_COLOR_PAIR =
            "INSERT INTO phone2color (phoneId, colorId)" +
                    "VALUES (?, ?)";

    public Optional<Phone> get(final Long key) {
        try {
            Phone phone = jdbcTemplate.queryForObject(FIND_PHONE_BY_ID, new BeanPropertyRowMapper<>(Phone.class), key);
            phone.setColors(findColors(phone.getId()));
            return Optional.of(phone);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(final Phone phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Phone must not be null");
        }
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(phone);
        Long phoneId = insertPhone.executeAndReturnKey(parameters).longValue();
        phone.setId(phoneId);
        saveColors(phone);
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query(FIND_ALL_PHONES, new BeanPropertyRowMapper(Phone.class), offset, limit);
        phones.forEach(p -> p.setColors(findColors(p.getId())));
        return phones;
    }

    private Set<Color> findColors(final Long phoneId) {
        return new HashSet<>((jdbcTemplate.query(FIND_COLORS_BY_PHONE_ID, new BeanPropertyRowMapper<>(Color.class), phoneId)));
    }

    private void saveColors(Phone phone) {
        Set<Color> colors = phone.getColors();
        colors.forEach(c -> jdbcTemplate.update(SAVE_PHONE_COLOR_PAIR, phone.getId(), c.getId()));
    }

}
