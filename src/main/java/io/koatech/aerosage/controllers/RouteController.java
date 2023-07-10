package io.koatech.aerosage.controllers;

import io.koatech.aerosage.models.Airport;
import io.koatech.aerosage.models.Route;
import io.koatech.aerosage.models.RouteResponse;
import io.koatech.aerosage.services.AirportService;
import io.koatech.aerosage.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "http://localhost:3000")
public class RouteController {


    @Autowired
    private RouteService routeService;

    @GetMapping("/search/{srcAirportCode}/{destAirportCode}/{maxStops}")
    public ResponseEntity<List<RouteResponse>> getAirportsByQueryString(@PathVariable String srcAirportCode, @PathVariable String destAirportCode, @PathVariable int maxStops) {
        List<RouteResponse> airportList = routeService.searchFlights(srcAirportCode, destAirportCode, maxStops);
        return ResponseEntity.ok().body(airportList);
    }

}
