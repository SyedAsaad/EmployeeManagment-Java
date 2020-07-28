package com.logex.demo.enums;

import java.util.HashMap;
import java.util.Map;

public enum VehicleType {
    FLAT_BED("Flat Bed"),
    OTHERS("Others");

    private String title;
    public static final Map<Integer, String> keyValues = new HashMap<>();

    static {
        for(VehicleType type : VehicleType.values()){
            keyValues.put(type.ordinal(), type.getTitle());
        }
    }

    public String getTitle() {
        return title;
    }

    VehicleType(String title) {
        this.title = title;
    }
}
