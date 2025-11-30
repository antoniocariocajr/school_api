package com.school.services;

import com.school.controllers.dto.person.PersonCreateDto;
import com.school.controllers.dto.person.PersonDto;
import com.school.controllers.dto.person.PersonUpdateDto;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PersonService {
    Page<PersonDto> findAll(Pageable page);
    PersonDto findById(UUID id);
    PersonDto create(PersonCreateDto dto);
    PersonDto update(UUID id, PersonUpdateDto dto);
    void delete(UUID id);
    void uploadPicture(UUID personId, MultipartFile file);
    Resource getPicture(UUID personId);
}
