package com.initialchecks.process.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCheck {
    // TODO: set this value
    private LocalDateTime createdAt;

    @NotBlank
    private String applicantId;
    @NotBlank
    private String depositId;
}
