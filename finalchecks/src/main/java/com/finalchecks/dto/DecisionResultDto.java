package com.finalchecks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecisionResultDto {
    private String applicantSystemId;
    private String depositSystemId;
    private String applicantDecision;
    private String depositDecision;
}
