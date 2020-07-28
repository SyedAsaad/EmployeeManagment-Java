package com.logex.demo.enums;

import java.util.HashMap;
import java.util.Map;

public enum VerifiedType {
    VERIFIED("Verified"),
    NOT_VERIFIED("Non Verified"),
    NOT_CHECK("Not Checked");

    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(VerifiedType type : VerifiedType.values()){
            keyValues.put(type.ordinal(), type.getTitle());
        }
    }

    private String title;

    VerifiedType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
