package io.koatech.aerosage.services;

import io.koatech.aerosage.models.Route;

import java.util.List;

public interface RouteService {
//    List<Flight> getAllFlights();
//    Flight getFlightById(long id);
    List<Route> searchFlights(String sourceAirportCode, String destinationAirportCode);
//    List<Flight> getFlightsByAirline(String airlineCode);
//    List<Flight> getFlightsByAirport(String airportCode);
//    Flight saveFlight(Flight flight);
}
