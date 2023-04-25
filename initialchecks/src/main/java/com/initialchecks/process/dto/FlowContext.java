package com.initialchecks.process.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowContext {

    private String flowUniqueId;
    private String flowName;
    private ApplicationCheck applicationCheck;
}
