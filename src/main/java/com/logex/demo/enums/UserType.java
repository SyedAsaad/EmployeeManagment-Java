package com.logex.demo.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserType {

    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    VIEWER("Viewer");

    private String title;
    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(UserType type : UserType.values()){
            keyValues.put(type.ordinal(), type.getTitle());
        }
    }

    UserType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
