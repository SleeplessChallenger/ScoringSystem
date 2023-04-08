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
public class Customer {
    @NotBlank
    private String id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String middleName;
    @NotNull
    private Integer age;
}
