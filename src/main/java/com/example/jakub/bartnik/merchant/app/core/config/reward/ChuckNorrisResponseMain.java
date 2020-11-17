package com.example.jakub.bartnik.merchant.app.core.config.reward;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ChuckNorrisResponseMain {

    public String getChuckNorrisJoke() throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.chucknorris.io/jokes/random"))
                .build();


        HttpResponse<String> resp = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        ChuckNorrisResponse response = objectMapper.
                readValue(resp.body(),
                        ChuckNorrisResponse.class);

        return response.getValue();
    }
}

