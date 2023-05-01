package com.feign.clients.finalchecks.feigninterfaces;

import com.feign.clients.finalchecks.dto.RejectDecision;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "final-checks")
public interface FinalChecksClient {
    @PostMapping(path = "api/v1/finalChecks/reject/{requestId}")
    void sendRejectDecision(@PathVariable("requestId") @NotBlank String requestId,
                            @RequestBody @Valid RejectDecision rejectDecision);
}
