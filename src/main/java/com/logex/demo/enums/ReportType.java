package com.logex.demo.enums;

import java.util.HashMap;
import java.util.Map;

public enum ReportType {
    INTERNAL_REPORT("Internal Report"),
    EXTERNAL_REPORT("External Report");

    private String title;

    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(ReportType type : ReportType.values()){
            keyValues.put(type.ordinal(), type.getTitle());
        }
    }

    ReportType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
