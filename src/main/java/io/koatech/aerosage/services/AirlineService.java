package io.koatech.aerosage.services;

import java.util.List;

import io.koatech.aerosage.models.Airline;

public interface AirlineService {
    List<Airline> getAllAirlines();
    Airline getAirlineByIata(String iata);
    List<Airline> searchAirlines(String searchTerm);
//    Airline saveAirline(Airline airline);
}
