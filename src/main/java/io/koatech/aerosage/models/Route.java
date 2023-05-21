package io.koatech.aerosage.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "routes")
public class Route {

    /*
    * {
      "_id": {
        "$oid": "642c46409ad37fd1f89b323e"
      },
      "Airline": "2B",
      "Airline ID": "410",
      "Source airport": "ASF",
      "Source airport ID": "2966",
      "Destination airport": "KZN",
      "Destination airport ID": "2990",
      "Codeshare": "",
      "Stops": "0",
      "Equipment": "CR2"
    }*/

    @Id
    @Field("_id")
    private String id;
    @Field("Airline")
    private String airline;
    @Field("Airline ID")
    private String airlineId;
    @Field("Source airport")
    private String srcAirport;
    @Field("Source airport ID")
    private String srcAirportId;
    @Field("Destination airport")
    private String destAirport;
    @Field("Destination airport ID")
    private String destAirportId;
    @Field("Codeshare")
    private String codeshare;
    @Field("Stops")
    private String stops;
    @Field("Equipment")
    private String equipment;

    public Route(String id, String airline, String airlineId, String srcAirport, String srcAirportId, String destAirport, String destAirportId, String codeshare, String stops, String equipment) {
        this.id = id;
        this.airline = airline;
        this.airlineId = airlineId;
        this.srcAirport = srcAirport;
        this.srcAirportId = srcAirportId;
        this.destAirport = destAirport;
        this.destAirportId = destAirportId;
        this.codeshare = codeshare;
        this.stops = stops;
        this.equipment = equipment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public String getSrcAirport() {
        return srcAirport;
    }

    public void setSrcAirport(String srcAirport) {
        this.srcAirport = srcAirport;
    }

    public String getSrcAirportId() {
        return srcAirportId;
    }

    public void setSrcAirportId(String srcAirportId) {
        this.srcAirportId = srcAirportId;
    }

    public String getDestAirport() {
        return destAirport;
    }

    public void setDestAirport(String destAirport) {
        this.destAirport = destAirport;
    }

    public String getDestAirportId() {
        return destAirportId;
    }

    public void setDestAirportId(String destAirportId) {
        this.destAirportId = destAirportId;
    }

    public String getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(String codeshare) {
        this.codeshare = codeshare;
    }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
