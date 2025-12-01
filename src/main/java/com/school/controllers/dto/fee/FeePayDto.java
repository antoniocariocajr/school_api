package com.school.controllers.dto.fee;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record FeePayDto(
        @NotNull LocalDate paidDate) {

}
