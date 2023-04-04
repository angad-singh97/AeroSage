package io.koatech.aerosage.repositories;

import io.koatech.aerosage.models.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends MongoRepository<Airport, String> {
    Airport findByIata(String iata);
    Airport findByIcao(String icao);
}
