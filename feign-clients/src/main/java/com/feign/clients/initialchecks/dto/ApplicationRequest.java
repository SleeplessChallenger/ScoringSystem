package com.feign.clients.initialchecks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {
    @NotBlank
    private String applicantId;
    @NotBlank
    private String depositId;
}
