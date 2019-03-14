package com.csulb.userapp.Repository;

import com.csulb.userapp.Entity.Route;
import com.csulb.userapp.Entity.RouteList;
import com.csulb.userapp.Entity.Stop;

import java.util.HashMap;
import java.util.Map;

public class RouteRepository {

    private static RouteRepository sInstance;

    private RouteList routeList;

    /**
     * Singleton Pattern for create / get instance.
     *
     * @return the instance.
     */
    public static synchronized RouteRepository getInstance() {
        if (sInstance == null) {
            sInstance = new RouteRepository();
        }
        return sInstance;
    }

    public RouteRepository() {
        Map<Integer, Route> routes = new HashMap<>();
        routes.put(0, new Route("Route 66"));
        routes.get(0).stopList.put(0, new Stop("Loc 1", "Loc 1 address"));
        routes.get(0).stopList.put(1, new Stop("Loc 2", "Loc 2 address"));
        routes.get(0).stopList.put(2, new Stop("Loc 3", "Loc 3 address"));


        routes.put(1, new Route("Route Springfield"));
        routes.get(1).stopList.put(0, new Stop("Loc 4", "Loc 4 address"));
        routes.get(1).stopList.put(1, new Stop("Loc 5", "Loc 5 address"));
        routes.get(1).stopList.put(2, new Stop("Loc 6", "Loc 6 address"));
        routes.get(1).stopList.put(3, new Stop("Loc 7", "Loc 7 address"));


        routes.put(2, new Route("Gray Passage"));
        routes.get(2).stopList.put(0, new Stop("Loc 2", "Loc 2 address"));
        routes.get(2).stopList.put(1, new Stop("Loc 5", "Loc 5 address"));
        routes.get(2).stopList.put(2, new Stop("Loc 8", "Loc 8 address"));
        routes.get(2).stopList.put(3, new Stop("Loc 9", "Loc 9 address"));

        routeList = new RouteList(routes);
    }

    public RouteList getRoutes() {
        return routeList;
    }
}
