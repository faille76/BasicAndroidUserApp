package com.csulb.userapp.Entity;

import java.util.Map;

public class RouteList {
    private Map<Integer, Route> listRoute;

    public RouteList(Map<Integer, Route> pListRoute) {
        this.listRoute = pListRoute;
    }

    public Map<Integer, Route> getListRoute() {
        return this.listRoute;
    }
}
