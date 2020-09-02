package com.logex.demo.enums;

import com.logex.demo.model.Termination;

import java.util.HashMap;
import java.util.Map;

public enum TerminationStatus {

    APPROVED("Approved"),
    REJECTED("Rejected");

    private String title;

    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(TerminationStatus type : TerminationStatus.values()){
            keyValues.put(type.ordinal(), type.getTitle());
        }
    }


    TerminationStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
