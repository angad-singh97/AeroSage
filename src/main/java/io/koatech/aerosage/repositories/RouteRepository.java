package io.koatech.aerosage.repositories;

import io.koatech.aerosage.models.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends MongoRepository<Route, String> {
    List<Route> findByAirlineCode(String airlineCode);
    List<Route> findByDepartureAirportCode(String airportCode);
    List<Route> findByArrivalAirportCode(String airportCode);
}
