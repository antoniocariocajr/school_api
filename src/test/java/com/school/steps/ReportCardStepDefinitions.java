package com.school.steps;

import com.school.persistence.entities.*;
import com.school.persistence.repositories.*;
import com.school.services.ReportCardService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReportCardStepDefinitions {

    @Autowired
    private SchoolTermRepository termRepo;
    @Autowired
    private EnrollmentRepository enrollmentRepo;
    @Autowired
    private GradeRepository gradeRepo;
    @Autowired
    private ReportCardService reportCardService;
    @Autowired
    private ReportCardRepository reportCardRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private DisciplineRepository disciplineRepo;
    @Autowired
    private SchoolClassRepository classRepo;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private UUID termId;
    private UUID enrollmentId;

    @Given("a school term with enrollments and grades")
    public void a_school_term_with_enrollments_and_grades() {
        transactionTemplate.execute(status -> {
            // Create Term
            SchoolTerm term = SchoolTerm.builder()
                    .code("2025-CUCUMBER-" + UUID.randomUUID())
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusMonths(6))
                    .build();
            term = termRepo.save(term);
            termId = term.getId();

            // Create Discipline & Class
            Discipline disc = disciplineRepo
                    .save(Discipline.builder().name("Math").code("MAT-" + UUID.randomUUID()).build());
            SchoolClass schoolClass = classRepo.save(
                    SchoolClass.builder().discipline(disc).schoolTerm(term).code("CLASS-" + UUID.randomUUID()).build());

            // Create Student
            Person person = personRepo.save(Person.builder().name("Cucumber Student")
                    .email("cuke" + UUID.randomUUID() + "@test.com").cpf(UUID.randomUUID().toString().substring(0, 11))
                    .birthDate(LocalDate.now()).phone("123").build());
            Student student = studentRepo.save(Student.builder().person(person)
                    .enrollmentNumber("REG-" + UUID.randomUUID()).entryDate(LocalDate.now()).build());

            // Create Enrollment
            Enrollment enrollment = enrollmentRepo
                    .save(Enrollment.builder().student(student).schoolClass(schoolClass).build());
            enrollmentId = enrollment.getId();

            // Create Grades
            gradeRepo.save(Grade.builder().enrollment(enrollment).typeGrade(Grade.TypeGrade.P1)
                    .value(BigDecimal.valueOf(8.0)).evaluationDate(new Date()).build());
            gradeRepo.save(Grade.builder().enrollment(enrollment).typeGrade(Grade.TypeGrade.P2)
                    .value(BigDecimal.valueOf(9.0)).evaluationDate(new Date()).build());

            return null;
        });
    }

    @When("I request report card generation for the term")
    public void i_request_report_card_generation_for_the_term() {
        reportCardService.generateForTerm(termId);
    }

    @Then("report cards should be created for all enrollments")
    public void report_cards_should_be_created_for_all_enrollments() {
        List<ReportCard> cards = reportCardRepo.findBySchoolTermId(termId);
        assertFalse(cards.isEmpty());
        assertEquals(1, cards.size());
    }

    @Then("the final grade should be calculated correctly")
    public void the_final_grade_should_be_calculated_correctly() {
        ReportCard card = reportCardRepo.findByEnrollmentIdAndSchoolTermId(enrollmentId, termId).orElseThrow();
        // (8 + 9) / 2 = 8.5
        assertEquals(0, card.getFinalGrade().compareTo(BigDecimal.valueOf(8.5)));
    }
}
