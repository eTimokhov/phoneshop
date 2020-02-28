package com.es.core.model.phone;

public enum SortingDirection {
    ASCENDING("ASC"), DESCENDING("DESC");

    private String value;

    SortingDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
