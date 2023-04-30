package com.scoring.commons.dto.kafka;

import com.scoring.commons.enums.Decision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositDto {

    private String depositId;
    private Decision decision;
    private LocalDateTime decisionAtTime;
}
