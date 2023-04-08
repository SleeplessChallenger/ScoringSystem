package com.startscoring.process.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {
    @NotBlank
    private String depositType;
    @NotNull
    private Integer depositPrice;
    @NotNull
    private Integer depositAge;
}
