package com.initialchecks.process.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UuidUtils {

    public String generateUuid() {
        return  UUID.randomUUID().toString().replace("-", "");
    }
}
