package com.school.controllers.dto.address;

import com.school.persistence.entities.Address;
import com.school.persistence.entities.PersonAddress;

public record AddressCreateRequest(
        PersonAddress.TypeAddress typeAddress
) {
    public Address toEntity(){
        return null;
    }
}
