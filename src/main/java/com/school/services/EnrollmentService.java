package com.school.services;

import com.school.persistence.entities.Enrollment;

import java.util.UUID;

public interface EnrollmentService {
    Enrollment enrollStudent(UUID studentId, UUID classId);
}
