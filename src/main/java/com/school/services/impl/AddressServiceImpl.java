package com.school.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.school.controllers.dto.address.AddressCreateRequest;
import com.school.controllers.dto.address.AddressDto;
import com.school.persistence.entities.Address;
import com.school.persistence.repositories.AddressRepository;
import com.school.services.AddressService;
import com.school.services.mapper.AddressMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public AddressDto createAddress(AddressCreateRequest addressCreateRequest) {
        Address address = addressRepository.save(addressCreateRequest.toEntity());
        return AddressDto.fromEntity(address);
    }

    @Override
    public Page<AddressDto> getAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable).map(AddressDto::fromEntity);
    }

    @Override
    public AddressDto getAddressById(UUID id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        return AddressDto.fromEntity(address);
    }

    @Override
    public AddressDto updateAddress(UUID id, AddressCreateRequest addressCreateRequest) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressMapper.updateAddress(address, addressCreateRequest);
        return AddressDto.fromEntity(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(UUID id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found"));
        addressRepository.delete(address);
    }
}
