package br.com.mydrafts.ApiMyDrafts.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Media {

    MOVIE("movie"),
    TV("tv");

    Media(String value) {
        this.value = value;
    }

    private String value;

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
