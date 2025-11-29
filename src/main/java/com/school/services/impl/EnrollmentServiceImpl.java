package com.school.services.impl;

import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.SchoolClass;
import com.school.persistence.entities.Student;
import com.school.persistence.repositories.EnrollmentRepository;
import com.school.persistence.repositories.SchoolClassRepository;
import com.school.persistence.repositories.StudentRepository;
import com.school.services.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository classRepository;

    @Transactional
    @Override
    public Enrollment enrollStudent(UUID studentId, UUID classId) {
        if (enrollmentRepository.existsByStudentIdAndSchoolClassId(studentId, classId))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Estudante já cadastrado na classe");

        Student student = studentRepository.findById(studentId).orElseThrow();
        SchoolClass schoolClass = classRepository.findById(classId).orElseThrow();

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .schoolClass(schoolClass)
                .enrollmentDate(LocalDate.now())
                .status(Enrollment.Status.ACTIVE)
                .build();

        return enrollmentRepository.save(enrollment);
    }
}
