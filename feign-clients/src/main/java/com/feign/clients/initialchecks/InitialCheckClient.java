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
    // here we put interface of our target controller
    @PostMapping(path = "{requestId}")
    ResponseEntity<String> checkApplication(@PathVariable @NotBlank String requestId,
                                            @RequestBody @Valid ApplicationRequest applicationRequest);
}
