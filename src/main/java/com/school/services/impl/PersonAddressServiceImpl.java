package com.school.services.impl;

import com.school.controllers.dto.address.AddressDto;
import com.school.controllers.dto.personaddress.PersonAddressCreateRequest;
import com.school.persistence.entities.Address;
import com.school.persistence.entities.Person;
import com.school.persistence.entities.PersonAddress;
import com.school.persistence.repositories.AddressRepository;
import com.school.persistence.repositories.PersonAddressRepository;
import com.school.persistence.repositories.PersonRepository;
import com.school.services.PersonAddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PersonAddressServiceImpl implements PersonAddressService {

    private final AddressRepository addressRepository;
    private final PersonAddressRepository personAddressRepository;
    private final PersonRepository personRepository;

    @Transactional
    @Override
    public Address addPersonAddress(UUID personId, PersonAddressCreateRequest request) {

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        Address address = addressRepository.save(request.toAddress());
        person.addAddress(address, request.typeAddress());
        personRepository.save(person);// cascade já salva PersonAddress
        return address;
    }

    @Override
    public Page<AddressDto> getAddressesByPersonId(UUID id, Pageable pageable) {
        return personAddressRepository.getAddressesByPersonId(id, pageable);
    }

    @Override
    public AddressDto getCurrentAddressByPersonId(UUID id) {
        Address address = personAddressRepository.findCurrentAddressByPersonId(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        return AddressDto.fromEntity(address);
    }

    @Override
    public AddressDto findCurrentByPersonAndType(UUID personId, PersonAddress.TypeAddress typeAddress) {
        Address address = personAddressRepository
                .findCurrentByPersonAndTypeAddress(personId, typeAddress)
                .map(PersonAddress::getAddress)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        return AddressDto.fromEntity(address);
    }

    @Transactional
    @Override
    public AddressDto updateCurrentByPersonAndType(UUID personId, PersonAddressCreateRequest request) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        Address address = addressRepository.save(request.toAddress());
        person.addAddress(address, request.typeAddress());
        personRepository.save(person);// cascade já salva PersonAddress
        return AddressDto.fromEntity(address);
    }

    @Transactional
    @Override
    public void deleteCurrentByPersonAndType(UUID personId, PersonAddress.TypeAddress typeAddress) {
        if (personRepository.existsById(personId)) {
            personAddressRepository.deleteCurrentByPersonAndTypeAddress(personId, typeAddress);
        } else {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public void deleteCurrentAddressByPersonId(UUID id) {
        if (personRepository.existsById(id)) {
            personAddressRepository.deleteCurrentAddressByPersonId(id);
        } else {
            throw new ResponseStatusException(NOT_FOUND);
        }
    }
}
