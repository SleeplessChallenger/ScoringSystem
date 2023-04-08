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
public class ApplicantData {

    // TODO: later we will check salary, credit score and so on

    @Valid
    @NotNull
    private Customer customer;
    @Valid
    @NotNull
    private Deposit deposit;
}
