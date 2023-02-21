package br.com.mydrafts.apimydrafts.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.value.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        return null;
    }

}
