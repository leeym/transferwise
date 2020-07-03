package com.leeym.api;

import com.google.gson.Gson;
import com.leeym.common.APIToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BaseAPI {
    private final Stage stage;
    private final APIToken token;
    private final HttpClient client;

    public BaseAPI(Stage stage, APIToken token) {
        if (!stage.equals(token.getStage())) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + ".stage [" + stage +
                    "] doesn't match " + token.getClass().getSimpleName() + ".stage [" + token.getStage() + "]");
        }
        this.stage = stage;
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        this.token = token;
    }

    protected String getUriPrefix() {
        if (stage.equals(Stage.SANDBOX)) {
            return "https://api.sandbox.transferwise.tech";
        }
        throw new UnsupportedOperationException("Not ready to hit production");
    }

    protected String get(String path) {
        URI uri = URI.create(getUriPrefix() + path);
        HttpRequest request = HttpRequest.newBuilder()
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .build();
        try {
            System.err.println("GET " + uri);
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected String post(String path, Object object) {
        URI uri = URI.create(getUriPrefix() + path);
        String json = new Gson().toJson(object);
        HttpRequest request = HttpRequest.newBuilder()
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .build();
        System.err.println("POST " + uri);
        System.err.println(json);
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
