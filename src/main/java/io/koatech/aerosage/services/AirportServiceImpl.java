package io.koatech.aerosage.services;


import io.koatech.aerosage.apis.flightdata.AirportInfoAPI;
import io.koatech.aerosage.apis.models.AirportInfo;
import io.koatech.aerosage.models.Airport;
import io.koatech.aerosage.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService{


    private AirportRepository airportRepository;

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Airport getAirportByIataCode(String iataCode) {
        // Check if airport exists in database
        Optional<Airport> optionalAirport = Optional.ofNullable(airportRepository.findByIata(iataCode));

        if (optionalAirport.isPresent()) {
            // Airport exists in database, return it
            System.out.println("found in DB: IATA Code - " + iataCode);
            return optionalAirport.get();
        } else {
            // Airport doesn't exist in database, fetch from API and save to database
            AirportInfoAPI airportInfoAPI = new AirportInfoAPI();
            AirportInfo airportInfo = airportInfoAPI.getAirportInfo(iataCode);

            Airport airport = new Airport();
            airport.setName(airportInfo.getName());
            airport.setIata(airportInfo.getIataCode());
            airport.setIcao(airportInfo.getIcaoCode());
            airport.setCity(airportInfo.getCity());
            airport.setCountry(airportInfo.getCountry());
            // ...

            // Save airport to database
//            airportRepository.save(airport);

            return airport;
        }
    }

    // Other methods for CRUD operations on airports
    // ...
}
