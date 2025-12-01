package com.school.controllers.dto.person;

import java.time.LocalDate;
import java.util.UUID;

import com.school.persistence.entities.Person;

public record PersonDto(
                UUID id,
                String name,
                String email,
                String cpf,
                LocalDate birthDate,
                String phone,
                String pictureUrl // link da foto
) {
        public static PersonDto toDto(Person person) {
                return new PersonDto(
                                person.getId(),
                                person.getName(),
                                person.getEmail(),
                                person.getCpf(),
                                person.getBirthDate(),
                                person.getPhone(),
                                person.getPictureKey());
        }
}
