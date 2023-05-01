package com.scoring.commons.dto.kafka;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DepositDto implements KafkaDto {

    @JsonIgnore
    private static final String DTO_NAME = "DEPOSIT_DTO";

    private String flowUniqueId;
    private String depositId;
    private Decision decision;
    private LocalDateTime decisionAtTime;

    @JsonIgnore
    public String getDtoName() {
        return DTO_NAME;
    }
}
