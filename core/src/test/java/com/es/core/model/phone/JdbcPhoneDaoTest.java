package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/JdbcPhoneDaoTest-context.xml")
public class JdbcPhoneDaoTest {

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


}