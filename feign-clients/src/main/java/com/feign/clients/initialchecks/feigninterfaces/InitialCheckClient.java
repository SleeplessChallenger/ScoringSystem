package com.feign.clients.initialchecks.feigninterfaces;

import com.feign.clients.initialchecks.dto.ApplicationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "initial-checks")
public interface InitialCheckClient {
    // here we put interface of our target controller
    @PostMapping(path = "api/v1/initialChecks/{requestId}")
    void checkApplication(@PathVariable("requestId") @NotBlank String requestId,
                          @RequestBody @Valid ApplicationRequest applicationRequest);
}
