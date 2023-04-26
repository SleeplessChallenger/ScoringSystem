package com.initialchecks.process.dto;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String applicantId;
    @NotBlank
    private String depositId;

    private LocalDateTime createdAt;
}
