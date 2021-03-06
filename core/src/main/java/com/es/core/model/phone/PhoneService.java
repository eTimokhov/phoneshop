package com.es.core.model.phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService {
    Optional<Phone> getPhone(Long key);

    void savePhone(Phone phone);
}
