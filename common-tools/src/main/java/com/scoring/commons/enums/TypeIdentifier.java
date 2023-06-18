package com.scoring.commons.enums;

import lombok.Getter;

@Getter
public enum TypeIdentifier {
    APPLICANT("applicant"),
    DEPOSIT("deposit");

    public final String type;

    TypeIdentifier(String type) {
        this.type = type;
    }
}
