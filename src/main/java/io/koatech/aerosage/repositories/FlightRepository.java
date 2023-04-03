package io.koatech.aerosage.repositories;

import io.koatech.aerosage.models.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {
    List<Flight> findByAirlineCode(String airlineCode);
    List<Flight> findByDepartureAirportCode(String airportCode);
    List<Flight> findByArrivalAirportCode(String airportCode);
}
