package io.koatech.aerosage.services;

import io.koatech.aerosage.beans.RoutesGraph;
import io.koatech.aerosage.models.Route;
import io.koatech.aerosage.models.RouteResponse;
import io.koatech.aerosage.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    private RouteRepository routeRepository;
    private RoutesGraph routesGraph;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, RoutesGraph routesGraph) {
        this.routeRepository = routeRepository;
        this.routesGraph = routesGraph;
    }

    @Override
    public List<RouteResponse> searchFlights(String sourceAirportCode, String destinationAirportCode, int maxStops) {
        List<RouteResponse> result = new ArrayList<>();
        int src = Integer.parseInt(sourceAirportCode);
        int dest = Integer.parseInt(destinationAirportCode);
        result.addAll(routesGraph.getTopNRoutesBetween(src, dest,maxStops + 1,150));
        System.out.println("The graph has found the routes");
        return result;
    }
}
