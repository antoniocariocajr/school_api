package com.school.services;

import com.school.controllers.dto.personaddress.PersonAddressCreateRequest;
import com.school.persistence.entities.Address;
import com.school.persistence.entities.PersonAddress;

import java.util.UUID;

public interface PersonAddressService {
    Address addPersonAddress(UUID personId, PersonAddressCreateRequest request);

    Address findCurrentByPersonAndType(UUID personId, PersonAddress.TypeAddress typeAddress);

    Address updateCurrentByPersonAndType(UUID personId, PersonAddressCreateRequest request);

    void deleteCurrentByPersonAndType(UUID personId, PersonAddress.TypeAddress typeAddress);
}
