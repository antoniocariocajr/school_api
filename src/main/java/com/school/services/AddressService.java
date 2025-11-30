package com.school.services;

import java.util.UUID;

import com.school.controllers.dto.address.AddressCreateRequest;
import com.school.controllers.dto.address.AddressDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    AddressDto createAddress(AddressCreateRequest addressCreateRequest);

    Page<AddressDto> getAllAddresses(Pageable pageable);

    AddressDto getAddressById(UUID id);

    AddressDto updateAddress(UUID id, AddressCreateRequest addressCreateRequest);

    void deleteAddress(UUID id);
}
