package com.school.services.mapper;

import com.school.controllers.dto.guardian.GuardianCreateDto;
import com.school.controllers.dto.guardian.GuardianDto;
import com.school.controllers.dto.guardian.GuardianUpdateDto;
import com.school.controllers.dto.person.PersonDto;
import com.school.persistence.entities.Guardian;
import com.school.persistence.entities.Person;
import org.springframework.stereotype.Component;

@Component
public class GuardianMapper {

    public GuardianDto toDto(Guardian guardian) {
        PersonDto personDto = PersonDto.toDto(guardian.getPerson());
        return new GuardianDto(
                guardian.getId(),
                personDto,
                guardian.getKinship(),
                guardian.getFinancialResponsible(),
                guardian.getLegalResponsible(),
                guardian.getActive());
    }

    public Guardian toEntity(Person person, GuardianCreateDto dto) {
        return Guardian.builder()
                .person(person)
                .kinship(dto.kinship())
                .financialResponsible(dto.financialResponsible() != null ? dto.financialResponsible() : false)
                .legalResponsible(dto.legalResponsible() != null ? dto.legalResponsible() : false)
                .active(true)
                .build();
    }

    public void updateEntity(Guardian guardian, GuardianUpdateDto dto) {
        if (dto.kinship() != null) {
            guardian.setKinship(dto.kinship());
        }
        if (dto.financialResponsible() != null) {
            guardian.setFinancialResponsible(dto.financialResponsible());
        }
        if (dto.legalResponsible() != null) {
            guardian.setLegalResponsible(dto.legalResponsible());
        }
        if (dto.active() != null) {
            guardian.setActive(dto.active());
        }
    }
}
