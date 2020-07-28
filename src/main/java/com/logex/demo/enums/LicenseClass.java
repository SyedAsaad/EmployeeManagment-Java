package com.logex.demo.enums;

import java.util.HashMap;
import java.util.Map;

public enum LicenseClass {
    HTV("HTV"),
    LTV("LTV"),PSV("PSV");

    private String title;

    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(LicenseClass type : LicenseClass.values()){
            keyValues.put(type.ordinal(), type.getTitle());
        }
    }

    LicenseClass(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
