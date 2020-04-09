package com.es.core.model.phone;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Resource
    PhoneDao phoneDao;

    @Override
    public Optional<Phone> getPhone(Long key) {
        return phoneDao.get(key);
    }

    @Override
    public void savePhone(Phone phone) {
        phoneDao.save(phone);
    }

}
