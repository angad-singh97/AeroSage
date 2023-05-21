package io.koatech.aerosage.models;

import java.util.List;

public class RouteResponse {
    private String sourceAirportCode;
    private String sourceAirportName;
    private String destinationAirportCode;
    private String destinationAirportName;
    private List<RouteStop> stops;


    // constructor, getters, and setters


    public RouteResponse(String sourceAirportCode, String sourceAirportName, String destinationAirportCode, String destinationAirportName, List<RouteStop> stops) {
        this.sourceAirportCode = sourceAirportCode;
        this.sourceAirportName = sourceAirportName;
        this.destinationAirportCode = destinationAirportCode;
        this.destinationAirportName = destinationAirportName;
        this.stops = stops;
    }

    public String getSourceAirportCode() {
        return sourceAirportCode;
    }

    public String getSourceAirportName() {
        return sourceAirportName;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public String getDestinationAirportName() {
        return destinationAirportName;
    }

    public List<RouteStop> getStops() {
        return stops;
    }

    public static class RouteStop {
        private String airportCode;
        private String airportName;

        private String arrivedViaAirlineId;
        private String arrivedViaAirlineName;

        public RouteStop(String airportCode, String airportName, String arrivedViaAirlineId, String arrivedViaAirlineName) {
            this.airportCode = airportCode;
            this.airportName = airportName;
            this.arrivedViaAirlineId = arrivedViaAirlineId;
            this.arrivedViaAirlineName = arrivedViaAirlineName;
        }

        // constructor, getters, and setters

        public String getAirportCode() {
            return airportCode;
        }

        public String getAirportName() {
            return airportName;
        }

        public String getArrivedViaAirlineId() {
            return arrivedViaAirlineId;
        }

        public String getArrivedViaAirlineName() {
            return arrivedViaAirlineName;
        }
    }
}
