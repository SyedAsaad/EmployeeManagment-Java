package com.logex.demo.service;

import com.logex.demo.config.Utils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommonService {

    public static List<String> getStates() {
        return Arrays.asList(Utils.getStates());
    }

    public static List<String> getCities() {
        return Arrays.asList(Utils.getCities());
    }

}
