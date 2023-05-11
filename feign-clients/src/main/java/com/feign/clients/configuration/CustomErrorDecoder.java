package com.feign.clients.configuration;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private static final Set<Integer> ALL_EXCEPTIONS = Set.of(500, 502, 503, 504);

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = new ErrorDecoder.Default().decode(methodKey, response);
        log.error("Error Decoder = {}", exception.getMessage());

        if (exception instanceof RetryableException) {
            return exception;
        }

        if (ALL_EXCEPTIONS.contains(response.status())) {
            return new RetryableException(
                    response.status(),
                    response.reason(),
                    response.request().httpMethod(),
                    null,
                    response.request()
            );
        }

        return exception;
    }
}
