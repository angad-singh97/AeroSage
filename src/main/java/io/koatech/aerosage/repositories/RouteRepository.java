package io.koatech.aerosage.repositories;

import io.koatech.aerosage.models.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends MongoRepository<Route, String> {
    List<Route> findByAirline(String airline);
    List<Route> findBySrcAirportId(String srcAirportId);
    List<Route> findByDestAirportId(String destAirportId);
    List<Route> findBySrcAirportIdAndDestAirportId(String srcAirportId, String destAirportId);
}
