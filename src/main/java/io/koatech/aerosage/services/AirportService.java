package io.koatech.aerosage.services;

import java.util.List;

import io.koatech.aerosage.models.Airport;

public interface AirportService {
//    List<Airport> getAllAirports();
//    Airport getAirportByCode(String airportCode);
//    List<Airport> searchAirports(String searchTerm);
//    Airport saveAirport(Airport airport);

    Airport getAirportByIataCode(String iataCode);

    List<Airport> searchAirports(String searchTerm);

}
