package br.com.mydrafts.apimydrafts.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Media {

    MOVIE("movie"),
    TV("tv");

    private final String value;

    Media(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Media fromValue(String value) {
        for (Media media : Media.values()) {
            if (media.value.equalsIgnoreCase(value)) {
                return media;
            }
        }
        return null;
    }

}
