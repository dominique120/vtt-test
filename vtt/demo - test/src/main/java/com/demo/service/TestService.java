package com.demo.service;

import com.demo.dto.*;
import com.demo.util.Util;
import jakarta.annotation.PostConstruct;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TestService {

    private static final String URL = "http://localhost:8080/api";
    Util u = new Util();
    Gson gson = new Gson();

    ItemDTO generateRandomPayload(Boolean isNew) {

        return new ItemDTO(isNew ? null : u.getRandomLong(100L),
                u.getRandomName(),
                u.getRandomBoolean(),
                u.getRandomName(),
                u.getRandomInt(50),
                new Date().getTime(),
                new Date().getTime(),
                new TypeDTO(u.getRandomLong(3L)),
                new PackagingDTO(u.getRandomLong(2L)),
                new StateDTO(u.getRandomLong(3L)),
                new UnitDTO(u.getRandomLong(1L)));
    }

    Mono<String> sendPost(ItemDTO item) {
        WebClient client = WebClient.create(URL);
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/items");

        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(gson.toJson(item));

        headersSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();

        return headersSpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException().flatMap(Mono::error);
            }
        });

    }

    Mono<String> sendPut(ItemDTO item) {
        WebClient client = WebClient.create(URL);
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.put();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/items");

        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(gson.toJson(item));

        headersSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();

        return headersSpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException().flatMap(Mono::error);
            }
        });

    }

    Mono<String> sendGetOne(Long id) {
        WebClient client = WebClient.create(URL);
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/items/" + id.toString());


        bodySpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();

        return bodySpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException().flatMap(Mono::error);
            }
        });
    }

    Mono<String> sendDelete(Long id) {
        WebClient client = WebClient.create(URL);
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.DELETE);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/items/" + id.toString());


        bodySpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve();

        return bodySpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode().is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException().flatMap(Mono::error);
            }
        });
    }

    @PostConstruct
    private void runSim() {

        // create a few values to play with
        for (int i = 0; i < 100; ++i) {
            try {
                sendPost(generateRandomPayload(true)).block();
            } catch (WebClientResponseException e) {
                System.out.print(e.getStatusCode() + "\n");
            }
        }

        System.out.print("init done\n");

        // run 1000 operations
        for (int i = 0; i < 1000; ++i) {
            int v = u.getRandomInt(4);

            switch (v) {

                // create a new value
                case 1:
                    try {
                        sendPost(generateRandomPayload(true)).block();
                    } catch (WebClientResponseException e) {
                        System.out.print(e.getStatusCode() + "\n");
                    }
                case 2:
                    try {
                        sendPut(generateRandomPayload(false)).block();
                    } catch (WebClientResponseException e) {
                        System.out.print(e.getStatusCode() + "\n");
                    }
                case 3:
                    try {
                        sendGetOne(u.getRandomLong(100L)).block();
                    } catch (WebClientResponseException e) {
                        System.out.print(e.getStatusCode() + "\n");
                    }
                case 4:
                    try {

                    } catch (WebClientResponseException e) {
                        System.out.print(e.getStatusCode() + "\n");
                    }

                default:
                    break;
            }
        }

    }

}
