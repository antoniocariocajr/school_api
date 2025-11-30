package com.school.persistence.repositories;

import com.school.persistence.entities.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuardianRepository extends JpaRepository<Guardian, UUID> {
    boolean existsByPersonId(UUID personId);
}
