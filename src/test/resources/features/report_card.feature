Feature: Report Card Generation

  Scenario: Generate report cards for a school term
    Given a school term with enrollments and grades
    When I request report card generation for the term
    Then report cards should be created for all enrollments
    And the final grade should be calculated correctly
