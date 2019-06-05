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
    @Resource
    private BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper;
    @Resource
    private BeanPropertyRowMapper<Color> colorBeanPropertyRowMapper;

    private SimpleJdbcInsert insertPhone;


    @PostConstruct
    public void init() {
        insertPhone = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("phones")
                .usingGeneratedKeyColumns("id");
    }

    private static final String FIND_COLORS_BY_PHONE_ID =
            "SELECT * FROM colors " +
                    "JOIN phone2color ON colors.id = phone2color.colorId " +
                    "WHERE phoneId = ?";

    private static final String FIND_ALL_PHONES =
            "SELECT * FROM phones OFFSET ? LIMIT ?";

    private static final String FIND_PHONE_BY_ID =
            "SELECT * FROM phones WHERE id = ?";

    private static final String SAVE_PHONE_COLOR_PAIR =
            "INSERT INTO phone2color (phoneId, colorId)" +
                    "VALUES (?, ?)";

    private final static String UPDATE_PHONE =
            "UPDATE phones SET " +
                    "brand = ?, model = ?, price = ?, displaySizeInches = ?, weightGr = ?, lengthMm = ?, " +
                    "widthMm = ?, heightMm = ?, announced = ?, deviceType = ?, os = ?, displayResolution = ?, " +
                    "pixelDensity = ?, displayTechnology = ?, backCameraMegapixels = ?, frontCameraMegapixels = ?, " +
                    "ramGb = ?, internalStorageGb = ?, batteryCapacityMah = ?, talkTimeHours = ?, " +
                    "standByTimeHours = ?, bluetooth = ?, positioning = ?, imageUrl = ?, description = ? " +
                    "WHERE id = ?";

    private static final String DELETE_COLORS_WITH_PHONE_ID =
            "DELETE FROM phone2color WHERE phoneId = ?";

    public Optional<Phone> get(final Long key) {
        try {
            Phone phone = jdbcTemplate.queryForObject(FIND_PHONE_BY_ID, phoneBeanPropertyRowMapper, key);
            phone.setColors(findColors(phone.getId()));
            return Optional.of(phone);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(final Phone phone) {
        Objects.requireNonNull(phone);
        if (phone.getId() != null) {
            update(phone);
        } else {
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(phone);
            Long phoneId = insertPhone.executeAndReturnKey(parameters).longValue();
            phone.setId(phoneId);
            saveColors(phone);
        }
    }

    private void update(Phone phone) {
        jdbcTemplate.update(UPDATE_PHONE, phone.getBrand(), phone.getModel(), phone.getPrice(),
                phone.getDisplaySizeInches(), phone.getWeightGr(), phone.getLengthMm(),
                phone.getWidthMm(), phone.getHeightMm(), phone.getAnnounced(),
                phone.getDeviceType(), phone.getOs(), phone.getDisplayResolution(),
                phone.getPixelDensity(), phone.getDisplayTechnology(), phone.getBackCameraMegapixels(),
                phone.getFrontCameraMegapixels(), phone.getRamGb(), phone.getInternalStorageGb(),
                phone.getBatteryCapacityMah(), phone.getTalkTimeHours(), phone.getStandByTimeHours(),
                phone.getBluetooth(), phone.getPositioning(), phone.getImageUrl(),
                phone.getDescription(), phone.getId());
        jdbcTemplate.update(DELETE_COLORS_WITH_PHONE_ID, phone.getId());
        saveColors(phone);
    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query(FIND_ALL_PHONES, phoneBeanPropertyRowMapper, offset, limit);
        phones.forEach(p -> p.setColors(findColors(p.getId())));
        return phones;
    }

    private Set<Color> findColors(final Long phoneId) {
        return new HashSet<>((jdbcTemplate.query(FIND_COLORS_BY_PHONE_ID, colorBeanPropertyRowMapper, phoneId)));
    }

    private void saveColors(Phone phone) {
        Set<Color> colors = phone.getColors();
        colors.forEach(c -> jdbcTemplate.update(SAVE_PHONE_COLOR_PAIR, phone.getId(), c.getId()));
    }

}
