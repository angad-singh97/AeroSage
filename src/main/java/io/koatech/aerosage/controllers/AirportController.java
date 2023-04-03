package io.koatech.aerosage.controllers;

import io.koatech.aerosage.models.Airport;
import io.koatech.aerosage.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping("/{iataCode}")
    public ResponseEntity<Airport> getAirportByIcaoCode(@PathVariable String iataCode) {
        Airport airport = airportService.getAirportByIataCode(iataCode);
        return ResponseEntity.ok().body(airport);
    }
}
