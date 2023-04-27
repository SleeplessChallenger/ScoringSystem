package com.scoring.commons.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UuidUtils {

    public static String generateUuid() {
        return  UUID.randomUUID().toString().replace("-", "");
    }
}
