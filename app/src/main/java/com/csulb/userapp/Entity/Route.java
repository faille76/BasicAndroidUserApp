package com.csulb.userapp.Entity;

import java.util.HashMap;
import java.util.Map;

public class Route {
    public String name;

    public Map<Integer, Stop> stopList = new HashMap<>();

    public Route(String pName) {
        this.name = pName;
    }
}
