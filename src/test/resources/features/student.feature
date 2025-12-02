Feature: Student Management

  Scenario: Create a new student successfully
    Given a valid student payload
    When I send a POST request to "/api/students"
    Then the response status should be 201
    And the response should contain the student's name

  Scenario: Fail to create student with existing CPF
    Given a student payload with an existing CPF
    When I send a POST request to "/api/students"
    Then the response status should be 400 or 422
