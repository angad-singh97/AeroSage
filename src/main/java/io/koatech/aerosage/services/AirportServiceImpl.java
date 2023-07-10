package io.koatech.aerosage.services;


import io.koatech.aerosage.apis.flightdata.AirportInfoAPI;
import io.koatech.aerosage.apis.models.AirportInfo;
import io.koatech.aerosage.models.Airport;
import io.koatech.aerosage.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Airport> searchAirports(String searchTerm) {
        System.out.println("Received Search Term:" + searchTerm);
        List<Airport> allAirports = airportRepository.findAll();
        return allAirports.stream()
                .filter(airport -> airport.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        airport.getCity().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        airport.getIcao().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        airport.getIata().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        airport.getAirportID().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        airport.getCountry().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }


    // Other methods for CRUD operations on airports
    // ...
}
