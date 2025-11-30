package com.school.persistence.repositories;

import com.school.persistence.entities.Person;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByEmail(String email);

    boolean existsByEmail(@Email String email);

    boolean existsByCpf(@CPF String cpf);
}
