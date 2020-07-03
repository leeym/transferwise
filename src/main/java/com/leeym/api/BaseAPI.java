package com.leeym.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leeym.common.APIToken;
import com.leeym.common.LocalDateTypeAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class BaseAPI {
    protected final Gson gson;
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
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
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
        System.err.println(">>> GET " + uri);
        final String body;
        try {
            body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.err.println("<<< " + body);
        return body;
    }

    protected String post(String path, Object object) {
        URI uri = URI.create(getUriPrefix() + path);
        String json = gson.toJson(object);
        HttpRequest request = HttpRequest.newBuilder()
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .build();
        System.err.println(">>> POST " + uri);
        System.err.println(">>> " + json);
        final String body;
        try {
            body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.err.println("<<< " + body);
        return body;
    }
}
