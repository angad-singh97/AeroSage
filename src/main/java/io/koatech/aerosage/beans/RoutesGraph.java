package io.koatech.aerosage.beans;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.koatech.aerosage.models.Airline;
import io.koatech.aerosage.models.Airport;
import io.koatech.aerosage.models.Route;
import io.koatech.aerosage.models.RouteResponse;
import io.koatech.aerosage.repositories.AirlineRepository;
import io.koatech.aerosage.repositories.AirportRepository;
import io.koatech.aerosage.repositories.RouteRepository;
import org.apache.commons.math3.util.FastMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Configuration
@PropertySource("classpath:application.properties")
public class RoutesGraph {
    private Map<Integer, TreeSet<WeightedEdge>> adjacencyList = new TreeMap<>();
    public static final double RADIUS_OF_EARTH_KM = 6371.01;
    public static final double DEFAULT_HAVERSINE_DISTANCE = 0.0;
    private Map<Integer, Airport> airportMap;
    private Map<Integer, Airline> airlineMap;

    public static double calcHaversine(double lat1, double lon1, double lat2, double lon2) {
        double radianLat1 = FastMath.toRadians(lat1);
        double radianLat2 = FastMath.toRadians(lat2);
        double radianLon1 = FastMath.toRadians(lon1);
        double radianLon2 = FastMath.toRadians(lon2);

        double dLat = radianLat2 - radianLat1;
        double dLon = radianLon2 - radianLon1;
        double a = FastMath.pow(FastMath.sin(dLat / 2), 2) + FastMath.cos(radianLat1) * FastMath.cos(radianLat2) * FastMath.pow(FastMath.sin(dLon / 2), 2);
        double c = 2 * FastMath.atan2(FastMath.sqrt(a), FastMath.sqrt(1 - a));

        return RADIUS_OF_EARTH_KM * c;
    }
    @Autowired
    public RoutesGraph(@Value("${appDataDir}") String appDataDir, RouteRepository routeRepository, AirportRepository airportRepository, AirlineRepository airlineRepository) {
        // Add pseudocode for loading graph from database here
        // 1. First check if a adjacencyList file already exists, and read that if you can into a graph object
        initAirportReferenceMap(airportRepository);
        initAirlineReferenceMap(airlineRepository);
        File adjacencyListFile = new File(appDataDir + "/adjList.txt");
        if (!adjacencyListFile.exists()) {
            initAdjListFile(routeRepository, airportRepository, adjacencyListFile);
        }
        readAdjListFile(adjacencyListFile);
        System.out.println("Graph is ready.");
    }

    private void initAirportReferenceMap(AirportRepository airportRepository) {
        List<Airport> allAirports = airportRepository.findAll();
        airportMap =  allAirports.stream()
                .collect(Collectors.toMap(
                        airport -> Integer.parseInt(airport.getAirportID()),
                        airport -> airport
                ));
    }


    private void initAirlineReferenceMap(AirlineRepository airlineRepository) {
        List<Airline> allAirlines = airlineRepository.findAll();
        airlineMap =  allAirlines.stream()
                .collect(Collectors.toMap(
                        airline -> Integer.parseInt(airline.getAirlineID()),
                        airline -> airline
                ));
    }


    private void readAdjListFile(File adjacencyListFile) {
        // read the file into the graph object
        //read the file into the adjList map
        System.out.println("The file was found.");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonParser jsonParser = objectMapper.getFactory().createParser(adjacencyListFile);
            adjacencyList = objectMapper.readValue(jsonParser, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initAdjListFile(RouteRepository routeRepository, AirportRepository airportRepository, File adjacencyListFile) {
        try {
            /*
            * issues identified here
            *
            * 1. airports in routes data aren't always in the airports data
            * 1b. I added these airports as vertices in the graph, but since they're not in the airports set, I cannot
            * fetch their latitude or longitude to get the edge weight.
            * 2. airport IDs are sometimes blank - how will be use that?
            * 3. routes are sometimes loops!
            * */
            // 2.Else make a call to the DB, gather all routes
            List<Route> allRoutes = routeRepository.findAll();
            // 3. Make another call to the DB, gather all airports
            List<Airport> allAirports = airportRepository.findAll();
            List<String> airportIds = allAirports.stream()
                    .map(Airport::getAirportID)
                    .toList();
            // 4. Now populate the adjacency list with all the keys being the airport IDs
            for (String airportId: airportIds) {
                adjacencyList.put(Integer.parseInt(airportId), new TreeSet<>());
            }
            // 4. Now populate the adjacency list with all available entries as per the routes, into each airport's list
            for (Route route: allRoutes) {
                String srcAirportId = route.getSrcAirportId();
                String destAirportId = route.getDestAirportId();

                int key = 0;
                int val = 0;

                if (srcAirportId.equals("\\N") || destAirportId.equals("\\N") ) {
//                    System.out.println("Source Airport : " + srcAirportId + " is blank. Skipping it");
//                    System.out.println("Dest Airport : " + destAirportId + " is blank. Skipping it");
                    continue;
                }
                key = Integer.parseInt(srcAirportId);
                val = Integer.parseInt(destAirportId);


                //I am skipping adding the entry to the adjList at all, even if just the dest airport is not known
                //this way aage bhi the src/dest airport will never cause an issue
                if (adjacencyList.get(key) == null || adjacencyList.get(val) == null ) {
//                    System.out.println("Airport : " + srcAirportId + " is in a route but not in the airport set. Skipping it");
                    continue;
                }

                Integer airlineId;
                try {
                    airlineId = Integer.parseInt(route.getAirlineId());
                } catch (NumberFormatException e) {
//                    System.out.println("AIRLINE ID IS NULL! - skipping this route.");
//                    System.out.println("Key = " + key + " Value: "+ val);
                    continue;
                }


                double haversine;
                Airport srcAirport = airportMap.get(key);
                Airport destAirport = airportMap.get(val);
                try {
                    double latitudeA = Double.parseDouble(srcAirport.getLatitude());
                    double latitudeB = Double.parseDouble(destAirport.getLatitude());
                    double longitudeA = Double.parseDouble(srcAirport.getLongitude());
                    double longitudeB = Double.parseDouble(destAirport.getLongitude());
                    haversine = calcHaversine(latitudeA, longitudeA, latitudeB, longitudeB);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    haversine = DEFAULT_HAVERSINE_DISTANCE;
                }
                adjacencyList.get(key).add(new WeightedEdge(key, val, airlineId,airlineMap.get(airlineId).getAirlineName(), haversine));


            }
            // 5. Save this adjacency list to a file to avoid future computing like this
            ObjectMapper objectMapper = new ObjectMapper();
            String adjacencyListJson = objectMapper.writeValueAsString(adjacencyList);
            FileWriter writer = new FileWriter(adjacencyListFile);
            writer.write(adjacencyListJson);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RouteResponse> getTopNRoutesBetween(int src, int dest, int maxStops, int resultsCount) {
        List<RouteResponse> result = new ArrayList<>();
        List<WeightedPath> topXPaths = getTopXPaths(src, dest, maxStops, resultsCount);
        //now iterate and create RouteResponse for each possible route
        for (WeightedPath eachPath: topXPaths) {
            RouteResponse.RouteStops routeStopList = getRouteStops(eachPath);
            RouteResponse routeResponse = new RouteResponse(Integer.toString(src), airportMap.get(src).getName(), Integer.toString(dest), airportMap.get(dest).getName(), eachPath.getTotalWeight(), eachPath.getTimeTaken(), routeStopList);
            result.add(routeResponse);
        }
        //return result
        return result;
    }

    private RouteResponse.RouteStops getRouteStops(WeightedPath eachPath) {
        List<RouteResponse.RouteStop> routeStopList = new ArrayList<>();
        List<WeightedEdge> path = eachPath.getPath();
        for (WeightedEdge edge: path) {
            int source = edge.getSource();
            int destination = edge.getDestination();
            String airlineId = Integer.toString(edge.getAirlineCode());
            String airlineName = airlineMap.get(edge.getAirlineCode()).getAirlineName();
            RouteResponse.RouteStop routeStop = new RouteResponse.RouteStop(Integer.toString(source), airportMap.get(source).getName(),Integer.toString(destination), airportMap.get(destination).getName(), airlineId, airlineName);
            routeStopList.add(routeStop);
        }
        return new RouteResponse.RouteStops(routeStopList);
    }

    private List<WeightedPath> getTopXPaths(int src, int dest, int maxStops, int resultsCount) {
        List<WeightedPath> weightedPathList = new ArrayList<>();
//        System.out.println("DFS Started");
        Airport srcAirport = airportMap.get(src);
        Airport destAirport = airportMap.get(dest);

        double offsetDist = calcHaversine((Double.parseDouble(srcAirport.getLatitude())), (Double.parseDouble(srcAirport.getLongitude())), (Double.parseDouble(destAirport.getLatitude())), (Double.parseDouble(destAirport.getLongitude())));
        double cutoffDist = (offsetDist*1.05);
        if (offsetDist <= 2500) {
            cutoffDist = offsetDist*1.5;
        }
        recursiveDFS(src, maxStops, new HashSet<>(), new WeightedPath(src, dest),weightedPathList, cutoffDist);
//        System.out.println("DFS Completed");
        //sort the received results by distance - ascending
//        System.out.println("Sorting..");
        Collections.sort(weightedPathList);
        //pick {resultsCount} number of results
//        System.out.println("Filtering..");
        List<WeightedPath> topXPaths = weightedPathList.stream()
                .limit(resultsCount)
                .collect(Collectors.toList());
        return topXPaths;
    }

    public void recursiveDFS(int current, int k, Set<Integer> visitedNodes, WeightedPath currentPath, List<WeightedPath>  allPathsFoundSoFar, double cutoffDist) {
//        if (cutoffDist != -1 && currentPath.getTotalWeight() >= cutoffDist || currentPath.size() + 1 > k + 1 || visitedNodes.contains(current)) {
        if (currentPath.size() + 1 > k + 1 || visitedNodes.contains(current)) {
            return;
        }
        if (current == currentPath.getDestAirport()) {
/*            System.out.print("Route: ");
            for (WeightedEdge c: currentPath) {
                System.out.print("From: " + c.getSource() + ", To: " + c.getDestination() + ", Weight: " + c.getWeight());
            }
            System.out.println("\n\n");*/
//            if (currentPath.size() + 1 > 2) {
//                System.out.println("Route Found: " + allPathsFoundSoFar.size());
//            }
            allPathsFoundSoFar.add(new WeightedPath(currentPath));
            return;
        }
        visitedNodes.add(current);
        for (WeightedEdge edge: adjacencyList.get(current)) {
            int neighborDestination = edge.getDestination();
            double edgeWeight = edge.getWeight();
            if (!visitedNodes.contains(neighborDestination)) {
                currentPath.addEdge(edge);
                currentPath.addWeight(edgeWeight);
                recursiveDFS(neighborDestination, k, visitedNodes, currentPath, allPathsFoundSoFar, cutoffDist);
                currentPath.removeWeight(edgeWeight);
                currentPath.removeLastEdge();
            }
        }
        visitedNodes.remove(current);
    }

    public static class WeightedPath implements Comparable<WeightedPath> {
        private List<WeightedEdge> path = new ArrayList<>();
        private double totalWeight = 0.0;

        private int sourceAirport;

        private int destAirport;

        private double timeTaken;

        public WeightedPath() {
        }

        public WeightedPath(WeightedPath otherPath) {
            sourceAirport = otherPath.getSourceAirport();
            destAirport = otherPath.getDestAirport();
            path.addAll(otherPath.getPath());
            totalWeight = otherPath.getTotalWeight();
            timeTaken = totalWeight/875.5;
        }

        public int size() {
            return path.size();
        }

        public WeightedPath(int sourceAirport, int destAirport) {
            this.sourceAirport = sourceAirport;
            this.destAirport = destAirport;
        }

        public List<WeightedEdge> getPath() {
            return path;
        }

        public double getTotalWeight() {
            return totalWeight;
        }

        public void addWeight(double addedWeight) {
            this.totalWeight += addedWeight;
        }
        public void removeWeight(double removedWeight) {
            this.totalWeight -= removedWeight;
        }

        public void addEdge(WeightedEdge edge) {
            this.path.add(edge);
        }

        public void removeLastEdge() {
            this.path.remove(path.size()-1);
        }

        public int getSourceAirport() {
            return sourceAirport;
        }

        public int getDestAirport() {
            return destAirport;
        }

        public double getTimeTaken() {
            return timeTaken;
        }

        @Override
        public int compareTo(WeightedPath o) {
            if (this.getTotalWeight() < o.getTotalWeight()) {
                return -1;
            } else if (this.getTotalWeight() > o.getTotalWeight()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    public static class WeightedEdge implements Comparable<WeightedEdge>{

        private int source;
        private int destination;

        private int airlineCode;

        private String airlineName;
        private double weight;

        public WeightedEdge() {
        }

        public WeightedEdge(int source, int destination, int airlineCode, String airlineName, double weight) {
            this.source = source;
            this.destination = destination;
            this.airlineName = airlineName;
            this.airlineCode = airlineCode;
            this.weight = weight;
        }

        public int getSource() {
            return source;
        }

        public int getDestination() {
            return destination;
        }

        public double getWeight() {
            return weight;
        }

        public int getAirlineCode() {
            return airlineCode;
        }

        public String getAirlineName() {
            return airlineName;
        }

        @Override
        public int compareTo(WeightedEdge o) {
            return this.getDestination() < o.getDestination() ? -1 : 1;
        }
    }



}
