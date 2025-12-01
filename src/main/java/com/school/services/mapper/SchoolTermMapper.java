package com.school.services.mapper;

import org.springframework.stereotype.Component;

import com.school.controllers.dto.schoolterm.SchoolTermCreateDto;
import com.school.controllers.dto.schoolterm.SchoolTermDto;
import com.school.controllers.dto.schoolterm.SchoolTermUpdateDto;
import com.school.persistence.entities.SchoolTerm;

@Component
public class SchoolTermMapper {
    public SchoolTerm toEntity(SchoolTermCreateDto schoolTermCreateDto) {
        return SchoolTerm.builder()
                .code(schoolTermCreateDto.code())
                .startDate(schoolTermCreateDto.startDate())
                .endDate(schoolTermCreateDto.endDate())
                .build();
    }

    public SchoolTerm toEntity(SchoolTermDto schoolTermDto) {
        return SchoolTerm.builder()
                .id(schoolTermDto.id())
                .code(schoolTermDto.code())
                .startDate(schoolTermDto.startDate())
                .endDate(schoolTermDto.endDate())
                .active(schoolTermDto.active())
                .build();
    }

    public SchoolTermDto toDto(SchoolTerm schoolTerm) {
        return new SchoolTermDto(
                schoolTerm.getId(),
                schoolTerm.getCode(),
                schoolTerm.getStartDate(),
                schoolTerm.getEndDate(),
                schoolTerm.getActive());
    }

    public void updateEntity(SchoolTerm schoolTerm, SchoolTermUpdateDto schoolTermUpdateDto) {
        schoolTerm.setCode(schoolTermUpdateDto.code());
        schoolTerm.setStartDate(schoolTermUpdateDto.startDate());
        schoolTerm.setEndDate(schoolTermUpdateDto.endDate());
        schoolTerm.setActive(schoolTermUpdateDto.active());
    }
}
