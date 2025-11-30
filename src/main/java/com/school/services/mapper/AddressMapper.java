package com.school.services.mapper;

import com.school.controllers.dto.address.AddressCreateRequest;
import com.school.controllers.dto.address.AddressDto;
import com.school.persistence.entities.Address;

import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDto toDto(Address address) {
        if (address == null)
            return null;

        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry());
    }

    public Address toEntity(AddressDto addressDto) {
        if (addressDto == null)
            return null;

        return Address.builder()
                .id(addressDto.id())
                .street(addressDto.street())
                .city(addressDto.city())
                .state(addressDto.state())
                .zipCode(addressDto.zipCode())
                .country(addressDto.country())
                .build();
    }

    public Address toEntity(AddressCreateRequest addressCreateRequest) {
        if (addressCreateRequest == null)
            return null;

        return Address.builder()
                .street(addressCreateRequest.street())
                .city(addressCreateRequest.city())
                .state(addressCreateRequest.state())
                .zipCode(addressCreateRequest.zipCode())
                .country(addressCreateRequest.country())
                .build();
    }

    public Address updateAddress(Address address, AddressCreateRequest addressCreateRequest) {
        if (address == null || addressCreateRequest == null)
            return null;

        address.setStreet(addressCreateRequest.street());
        address.setCity(addressCreateRequest.city());
        address.setState(addressCreateRequest.state());
        address.setZipCode(addressCreateRequest.zipCode());
        address.setCountry(addressCreateRequest.country());

        return address;
    }
}
