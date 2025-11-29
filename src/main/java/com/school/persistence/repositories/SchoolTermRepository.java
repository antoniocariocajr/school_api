package com.school.persistence.repositories;

import com.school.persistence.entities.SchoolTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchoolTermRepository extends JpaRepository<SchoolTerm, UUID> {
}
