package com.school.services.mapper;

import com.school.controllers.dto.person.PersonCreateDto;
import com.school.controllers.dto.person.PersonDto;
import com.school.persistence.entities.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonDto toDto(Person p) {
        return new PersonDto(
                p.getId(),
                p.getName(),
                p.getEmail(),
                p.getCpf(),
                p.getBirthDate(),
                p.getPhone(),
                p.getPictureKey() != null ? "/api/persons/" + p.getId() + "/picture" : null
        );
    }

    public Person toEntity(PersonCreateDto dto) {
        return Person.builder()
                .name(dto.name())
                .email(dto.email())
                .cpf(dto.cpf())
                .birthDate(dto.birthDate())
                .phone(dto.phone())
                .build();
    }
}
