package com.school.controllers.dto.personaddress;

import com.school.persistence.entities.Address;
import com.school.persistence.entities.PersonAddress.TypeAddress;

public record PersonAddressCreateRequest(String street,
        String city,
        String state,
        String zipCode,
        String country,
        TypeAddress typeAddress) {

    public Address toAddress() {
        return Address.builder()
                .street(street)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .country(country)
                .build();
    }

}
