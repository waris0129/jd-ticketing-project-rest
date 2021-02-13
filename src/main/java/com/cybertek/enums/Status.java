package com.cybertek.enums;

public enum Status {

    OPEN("Open"),IN_PROGRESS("In Progress"),UAT_TEST("UAT Testing"),COMPLETE("Complete");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
