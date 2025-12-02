package com.school.controllers.dto.document;

import jakarta.validation.constraints.Size;

public record DocumentUpdateDto(@Size(max = 255) String description) {

}
