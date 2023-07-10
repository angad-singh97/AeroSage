package io.koatech.aerosage.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RouteResponse {
    private String sourceAirportCode;
    private String sourceAirportName;
    private String destinationAirportCode;
    private String destinationAirportName;
    private RouteStops stops;

    private double distance;

    private double timeTaken;


    // constructor, getters, and setters


    public RouteResponse(String sourceAirportCode, String sourceAirportName, String destinationAirportCode, String destinationAirportName, double distance, double timeTaken, RouteStops stops) {
        this.sourceAirportCode = sourceAirportCode;
        this.sourceAirportName = sourceAirportName;
        this.destinationAirportCode = destinationAirportCode;
        this.destinationAirportName = destinationAirportName;
        this.stops = stops;
        this.distance = distance;
        this.timeTaken = timeTaken;
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

    public double getDistance() {
        return distance;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public RouteStops getStops() {
        return stops;
    }

    public static class RouteStops {
        private List<RouteStop> routeStopList;
        private String routeStopListId;

        public RouteStops(List<RouteStop> routeStopList) {
            this.routeStopList = routeStopList;
            calculateRouteStopListId(routeStopList);
        }

        private void calculateRouteStopListId(List<RouteStop> routeStopList) {
            StringBuilder sb = new StringBuilder();
            for (RouteStop stop: routeStopList) {
                sb.append(stop.getRouteStopId());
            }

            sb.append(generateSalt());

            String hashedValue;
            try {
                hashedValue = getHashedValue(sb.toString(), "SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            this.routeStopListId = hashedValue.substring(0, 12);
            System.out.println(routeStopListId);
        }

        private static String generateSalt() {
            SecureRandom random = new SecureRandom();
            byte[] saltBytes = new byte[16];
            random.nextBytes(saltBytes);
            return bytesToHex(saltBytes);
        }

        private static String bytesToHex(byte[] bytes) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }

        private static String getHashedValue(String input, String algorithm) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        }

        public List<RouteStop> getRouteStopList() {
            return routeStopList;
        }

        public String getRouteStopListId() {
            return routeStopListId;
        }
    }
    public static class RouteStop {

        private String routeStopId;
        private String firstAirportCode;
        private String firstAirportName;
        private String secondAirportCode;
        private String secondAirportName;

        private String airlineId;
        private String airlineName;

        public RouteStop(String firstAirportCode, String firstAirportName, String secondAirportCode, String secondAirportName, String airlineId, String airlineName) {
            this.firstAirportCode = firstAirportCode;
            this.secondAirportCode = secondAirportCode;
            this.firstAirportName = firstAirportName;
            this.secondAirportName = secondAirportName;
            this.airlineId = airlineId;
            this.airlineName = airlineName;
            this.routeStopId = firstAirportCode+secondAirportCode;
        }

        // constructor, getters, and setters

        public String getFirstAirportCode() {
            return firstAirportCode;
        }

        public String getFirstAirportName() {
            return firstAirportName;
        }
        public String getSecondAirportCode() {
            return secondAirportCode;
        }

        public String getSecondAirportName() {
            return secondAirportName;
        }

        public String getAirlineId() {
            return airlineId;
        }

        public String getAirlineName() {
            return airlineName;
        }

        public String getRouteStopId() {
            return routeStopId;
        }
    }
}
