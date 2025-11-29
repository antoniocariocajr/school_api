package com.school.services;


import com.school.controllers.dto.address.AddressCreateRequest;
import com.school.persistence.entities.Address;
import com.school.persistence.entities.PersonAddress;

import java.util.UUID;

public interface PersonAddressService {
    Address addPersonAddress(UUID personId, AddressCreateRequest request);
    Address findCurrentByPersonAndType(UUID personId, PersonAddress.TypeAddress typeAddress);
}
