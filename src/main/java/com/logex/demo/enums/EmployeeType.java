package com.logex.demo.enums;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeType {

    DRIVER("Driver"),
    SUPERVISOR("Supervisor");

    private String title;
    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(EmployeeType type : EmployeeType.values()){
            keyValues.put(type.ordinal(), type.getTitle());
        }
    }

    EmployeeType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
