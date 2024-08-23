package com.example.springboot.dtos;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public record EmployeeRecordDto(
                @NotBlank String cpf,
                @NotBlank String name,
                @NotNull Date hireDate,
                @NotBlank String workFunction,
                @NotNull BigDecimal salary,
                UUID managerId,
                UUID[] listManaged) {
}
