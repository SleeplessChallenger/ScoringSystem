package com.startscoring.process.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {
    @NotNull
    private String depositId;
    @NotBlank
    private String depositType;
    @NotNull
    private BigDecimal depositPrice;
    @NotNull
    private Integer depositAge;
}
