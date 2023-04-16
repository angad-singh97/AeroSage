package io.koatech.aerosage.services;

import io.koatech.aerosage.models.Route;
import io.koatech.aerosage.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {


    private RouteRepository routeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<Route> searchFlights(String sourceAirportCode, String destinationAirportCode) {
        //first check the repo

        List<Route> repoResults = routeRepository.findBySrcAirportIdAndDestAirportId(sourceAirportCode, destinationAirportCode);

        //else check the graph model for indirect routes
        return repoResults;
    }
}
