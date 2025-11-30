package com.school.controllers.dto.address;

import java.util.UUID;

import com.school.persistence.entities.Address;

public record AddressDto(
                UUID id,
                String street,
                String city,
                String state,
                String zipCode,
                String country) {

        public static AddressDto fromEntity(Address address) {
                return new AddressDto(
                                address.getId(),
                                address.getStreet(),
                                address.getCity(),
                                address.getState(),
                                address.getZipCode(),
                                address.getCountry());
        }
}
