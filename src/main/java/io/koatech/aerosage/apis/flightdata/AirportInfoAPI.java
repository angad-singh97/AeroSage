package io.koatech.aerosage.apis.flightdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.koatech.aerosage.apis.models.AirportInfo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AirportInfoAPI {

    public AirportInfo getAirportInfo(String iataCode) {
        HttpRequest request = getHttpRequest(iataCode);
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            AirportInfo airportInfo = objectMapper.readValue(response.body(), AirportInfo.class);
            return airportInfo;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpRequest getHttpRequest(String iataCode) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://airport-info.p.rapidapi.com/airport?iata="+ iataCode))
                .header("X-RapidAPI-Key", "e68d07e223msh3c6db2a5008cb33p151b93jsnaf18c911c686")
                .header("X-RapidAPI-Host", "airport-info.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return request;
    }

}
