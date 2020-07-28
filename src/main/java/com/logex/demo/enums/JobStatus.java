package com.logex.demo.enums;

import java.util.HashMap;
import java.util.Map;

public enum JobStatus {
    RESERVE_ON_POOL("Reserve On Pool"),
    RESERVE_DRIVER("Reserve Driver"),
    ACTIVE_DRIVER("Active Driver");


    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(JobStatus type : JobStatus.values()){
            keyValues.put(type.ordinal(), type.getTitle());
        }
    }

    JobStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private String title;
}
