package io.koatech.aerosage.controllers;

import io.koatech.aerosage.models.Airline;
import io.koatech.aerosage.services.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {
    @Autowired
    private AirlineService airlineService;

    @GetMapping()
    public ResponseEntity<List<Airline>> getAllAirlines() {
        List<Airline> airlines = airlineService.getAllAirlines();
        return ResponseEntity.ok().body(airlines);
    }
}
