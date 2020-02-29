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

    @Override
    public List<Phone> findAll(int offset, int limit) {
        return phoneDao.findAll(offset, limit);
    }

    @Override
    public List<Phone> findAll(String searchTerms, String orderBy, SortingDirection sortingDirection, int offset, int limit) {
        return phoneDao.findAll(searchTerms, orderBy, sortingDirection, offset, limit);
    }

    @Override
    public int findTotalCount(String searchTerms) {
        return phoneDao.findTotalCount(searchTerms);
    }
}
