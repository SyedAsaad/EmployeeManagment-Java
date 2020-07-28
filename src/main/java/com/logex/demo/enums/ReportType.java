package com.logex.demo.enums;

public enum ReportType {
    INTERNAL_REPORT("Internal Report"),
    EXTERNAL_REPORT("External Report");

    private String title;

    ReportType(String title) {
        this.title = title;
    }
}
