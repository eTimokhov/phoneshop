package com.es.core.model.phone;

import com.es.core.IntegrationTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class JdbcPhoneDaoTest extends IntegrationTest {

    @Resource
    private PhoneDao jdbcPhoneDao;

    @Test
    public void testGetPhone() {
        Optional<Phone> optionalPhone = jdbcPhoneDao.get(1000L);
        assertTrue(optionalPhone.isPresent());
    }

    @Test
    public void testGetPhonePhoneIdMatch() {
        Phone phone = jdbcPhoneDao.get(1000L).get();
        assertEquals(Long.valueOf(1000L), phone.getId());
    }

    @Test
    public void testGetPhoneColorsMatch() {
        Phone phone = jdbcPhoneDao.get(1011L).get();
        Set<Long> colorIds = phone.getColors().stream().map(Color::getId).collect(Collectors.toSet());
        assertEquals(new HashSet<>(Arrays.asList(1000L, 1002L, 1003L)), colorIds);
    }

    @Test
    public void testGetNullPhone() {
        Optional<Phone> optionalPhone = jdbcPhoneDao.get(999L);
        assertFalse(optionalPhone.isPresent());
    }

    @Test
    public void testFindAllCountMatch() {
        List<Phone> phones = jdbcPhoneDao.findAll(0, 10);
        assertEquals(10, phones.size());
    }

    @Test
    public void testSavePhone() {
        Phone phone = new Phone();
        phone.setBrand("someBrand1");
        phone.setModel("someModel1");
        phone.setBatteryCapacityMah(3000);
        phone.setDisplaySizeInches(BigDecimal.valueOf(6.1));
        jdbcPhoneDao.save(phone);
        Long phoneId = phone.getId();
        Optional<Phone> optionalExtractedPhone = jdbcPhoneDao.get(phoneId);
        assertTrue(optionalExtractedPhone.isPresent());
        Phone extractedPhone = optionalExtractedPhone.get();
        assertEquals("someBrand1", extractedPhone.getBrand());
        assertEquals("someModel1", extractedPhone.getModel());
        assertEquals(Integer.valueOf(3000), extractedPhone.getBatteryCapacityMah());
        assertEquals(BigDecimal.valueOf(6.1), extractedPhone.getDisplaySizeInches());
    }

    @Test
    public void testSavePhoneColors() {
        Phone phone = new Phone();
        phone.setBrand("someBrand2");
        phone.setModel("someModel2");

        Set<Color> colors = getTestColorSet();
        phone.setColors(colors);

        jdbcPhoneDao.save(phone);
        Long phoneId = phone.getId();
        Phone extractedPhone = jdbcPhoneDao.get(phoneId).get();
        assertEquals(colors, extractedPhone.getColors());
    }

    @Test
    public void testUpdatePhone() {
        Phone phone = jdbcPhoneDao.get(1020L).get();
        Set<Color> colors = getTestColorSet();

        phone.setBrand("SomeBrand3");
        phone.setBatteryCapacityMah(3000);
        phone.setColors(colors);
        jdbcPhoneDao.save(phone);
        Phone updatedPhone = jdbcPhoneDao.get(1020L).get();
        assertEquals("SomeBrand3", phone.getBrand());
        assertEquals(Integer.valueOf(3000), phone.getBatteryCapacityMah());
        assertEquals(colors, updatedPhone.getColors());
    }

    @Test(expected=NullPointerException.class)
    public void testSaveNull() {
        jdbcPhoneDao.save(null);
    }

    private Set<Color> getTestColorSet() {
        Color color1 = new Color();
        color1.setCode("Blue");
        color1.setId(1003L);

        Color color2 = new Color();
        color2.setCode("Pink");
        color2.setId(1008L);

        Set<Color> colors = new HashSet<>();
        colors.add(color1);
        colors.add(color2);

        return colors;
    }

}