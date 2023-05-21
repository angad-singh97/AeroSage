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
    public List<RouteResponse> searchFlights(String sourceAirportCode, String destinationAirportCode) {
/*        first check the repo
        todo - exclude the zero count ones from the graph call - done
        routes already seem to have stops, wth - this was just 11 routes
        I like the idea of having a quick POJO to pass ahead instead of the whole route model class - this is now RouteResponse
        maybe a wrapper model class just for frontend use?
        else check the graph model for indirect routes - done
        now I need to get all indirect routes and turn them into Route objects also - So now we have all 1-3 stop routes.*/
        List<RouteResponse> result = new ArrayList<>();
        int src = Integer.parseInt(sourceAirportCode);
        int dest = Integer.parseInt(destinationAirportCode);
        result.addAll(routesGraph.getTopNRoutesBetween(src, dest,2,30));
        System.out.println("The graph has found the routes");
        List<Route> repoResults = routeRepository.findBySrcAirportIdAndDestAirportId(sourceAirportCode, destinationAirportCode);
        for (Route eachDBRoute: repoResults) {
            if (!eachDBRoute.getStops().equals("0")) {
                continue; //take only the direct routes
            }
            RouteResponse routeResponse = new RouteResponse(eachDBRoute.getSrcAirportId(), eachDBRoute.getSrcAirport(), eachDBRoute.getSrcAirportId(), eachDBRoute.getDestAirportId(), new ArrayList<>());
            result.add(routeResponse);
        }
        return result;
    }
}
