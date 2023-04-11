package com.feign.clients.initialchecks;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "initial-checks")
public interface InitialCheckClient {
    // TODO: move url to application.yaml
    // here we put interface of our target controller
    @PostMapping(path = "api/v1/initialChecks/{requestId}")
    ResponseEntity<String> checkApplication(@PathVariable("requestId") @NotBlank String requestId,
                                            @RequestBody @Valid ApplicationRequest applicationRequest);
}
