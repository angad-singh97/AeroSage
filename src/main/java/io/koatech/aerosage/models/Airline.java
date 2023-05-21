package io.koatech.aerosage.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "airlines")
public class Airline {


    /**
     * {
     *   "Airline ID": "3",
     *   "Name": "1Time Airline",
     *   "Alias": "\\N",
     *   "IATA": "1T",
     *   "ICAO": "RNX",
     *   "Callsign": "NEXTIME",
     *   "Country": "South Africa",
     *   "Active": "Y"
     * }
     */


    @Id
    @Field("_id")
    private String id;

    @Field("Name")
    private String airlineName;
    @Field("Airline ID")
    private String airlineID;

    @Field("Alias")
    private String alias;

    @Field("IATA")
    private String iata;
    @Field("ICAO")
    private String icao;
    @Field("Callsign")
    private String callsign;
    @Field("Country")
    private String country;
    @Field("Active")
    private String active;

    public Airline() {}

    public Airline(String id, String airlineName, String airlineID, String alias, String iata, String icao, String callsign, String country, String active) {
        this.id = id;
        this.airlineID = airlineID;
        this.airlineName = airlineName;
        this.alias = alias;
        this.iata = iata;
        this.icao = icao;
        this.callsign = callsign;
        this.country = country;
        this.active = active;
    }

    // Getters and setters


    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(String airlineID) {
        this.airlineID = airlineID;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
