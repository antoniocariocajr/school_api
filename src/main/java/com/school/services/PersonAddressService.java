package com.school.services;

import com.school.controllers.dto.address.AddressDto;
import com.school.controllers.dto.personaddress.PersonAddressCreateRequest;
import com.school.persistence.entities.Address;
import com.school.persistence.entities.PersonAddress;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonAddressService {
    Address addPersonAddress(UUID personId, PersonAddressCreateRequest request);

    AddressDto findCurrentByPersonAndType(UUID personId, PersonAddress.TypeAddress typeAddress);

    AddressDto updateCurrentByPersonAndType(UUID personId, PersonAddressCreateRequest request);

    void deleteCurrentByPersonAndType(UUID personId, PersonAddress.TypeAddress typeAddress);

    Page<AddressDto> getAddressesByPersonId(UUID id, Pageable pageable);

    AddressDto getCurrentAddressByPersonId(UUID id);

    void deleteCurrentAddressByPersonId(UUID id);
}
