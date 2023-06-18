package com.feign.clients.finalchecks.dto;

import com.scoring.commons.enums.TypeIdentifier;
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
public class RejectDecision {

    @NotBlank
    private String depositId;
    @NotBlank
    private String applicantId;
    @NotBlank
    private String uniqueFlowId;
    @NotNull
    private LocalDateTime sentTime;
    @NotNull
    private TypeIdentifier typeIdentifier;
}
