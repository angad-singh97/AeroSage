package io.koatech.aerosage.controllers;

import io.koatech.aerosage.models.Airport;
import io.koatech.aerosage.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@CrossOrigin(origins = "http://localhost:3000")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping("/getEntity/{iataCode}")
    public ResponseEntity<Airport> getAirportByIcaoCode(@PathVariable String iataCode) {
        Airport airport = airportService.getAirportByIataCode(iataCode);
        return ResponseEntity.ok().body(airport);
    }
    @GetMapping("/search/{queryString}")
    public ResponseEntity<List<Airport>> getAirportsByQueryString(@PathVariable String queryString) {
        List<Airport> airportList = airportService.searchAirports(queryString);
        return ResponseEntity.ok().body(airportList);
    }
}
