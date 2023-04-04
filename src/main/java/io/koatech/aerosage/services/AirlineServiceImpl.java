package io.koatech.aerosage.services;

import io.koatech.aerosage.models.Airline;
import io.koatech.aerosage.repositories.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirlineServiceImpl implements AirlineService {


    private AirlineRepository airlineRepository;
    @Autowired
    public AirlineServiceImpl(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    @Override
    public List<Airline> getAllAirlines() {
        Optional<List<Airline> > optionalAirlines = Optional.of(airlineRepository.findAll());

        if (optionalAirlines.isPresent()) {
            // Airport exists in database, return it
            System.out.println("returning all airlines from DB");
            return optionalAirlines.get();
        }
        return null;
    }

    @Override
    public Airline getAirlineByIata(String iata) {
        return null;
    }

    @Override
    public List<Airline> searchAirlines(String searchTerm) {
        return null;
    }
}
