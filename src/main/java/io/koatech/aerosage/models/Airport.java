package io.koatech.aerosage.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "airports")
public class Airport {

    /**
     * {
     *   "Airport ID": "2",
     *   "Name": "Madang Airport",
     *   "City": "Madang",
     *   "Country": "Papua New Guinea",
     *   "IATA": "MAG",
     *   "ICAO": "AYMD",
     *   "Latitude": "-5.20707988739",
     *   "Longitude": "145.789001465",
     *   "Altitude": "20",
     *   "Timezone": "10",
     *   "DST": "U"
     * }
     */


    @Id
    @Field("_id")
    private String id;
    @Field("Airport ID")
    private String airportID;
    @Field("Name")
    private String name;
    @Field("IATA")
    private String iata;
    @Field("ICAO")
    private String icao;
    @Field("City")
    private String city;
    @Field("Country")
    private String country;
    @Field("Latitude")
    private String latitude;
    @Field("Longitude")
    private String longitude;
    @Field("Altitude")
    private String altitude;
    @Field("Timezone")
    private String timezone;
    @Field("DST")
    private String dst;

    public Airport() {}

    public Airport(String id, String airportID, String name, String iata, String icao, String city, String country, String latitude, String longitude, String altitude, String timezone, String dst) {
        this.id = id;
        this.airportID = airportID;
        this.name = name;
        this.iata = iata;
        this.icao = icao;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
        this.dst = dst;
    }

// Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirportID() {
        return airportID;
    }

    public void setAirportID(String airportID) {
        this.airportID = airportID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }
}
