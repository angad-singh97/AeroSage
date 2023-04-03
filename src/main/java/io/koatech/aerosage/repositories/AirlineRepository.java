package io.koatech.aerosage.repositories;

import io.koatech.aerosage.models.Airline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends MongoRepository<Airline, String> {
    Airline findByAirlineCode(String airlineCode);
}
