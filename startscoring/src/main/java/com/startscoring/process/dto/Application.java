package com.startscoring.process.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Valid
    @NotNull
    private Applicant applicant;
    @Valid
    @NotNull
    private Deposit deposit;
}
