package com.leeym.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.leeym.common.ApiToken;
import com.leeym.common.LocalDateTimeTypeAdapter;
import com.leeym.common.LocalDateTypeAdapter;
import com.leeym.common.LocalTimeTypeAdapter;
import com.leeym.common.OffsetDateTimeTypeAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import static com.leeym.api.Stage.SANDBOX;

public class BaseApi {
    protected final Gson gson;
    private final Stage stage;
    private final ApiToken token;
    private final HttpClient client;

    public BaseApi(Stage stage, ApiToken token) {
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
                .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeAdapter())
                .setPrettyPrinting()
                .create();
    }

    protected String getUriPrefix() {
        if (stage.equals(SANDBOX)) {
            return "https://api.sandbox.transferwise.tech";
        }
        throw new UnsupportedOperationException("Not ready to hit production");
    }

    protected String get(String path) {
        URI uri = URI.create(getUriPrefix() + path);
        System.err.println(">>> GET " + uri);
        HttpRequest request = HttpRequest.newBuilder()
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .build();
        final String body;
        try {
            body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        JsonElement jsonElement = JsonParser.parseString(body);
        if (jsonElement.isJsonObject()) {
            System.err.println("<<< " + gson.toJson(jsonElement.getAsJsonObject()));
        } else if (jsonElement.isJsonArray()) {
            System.err.println("<<< " + gson.toJson(jsonElement.getAsJsonArray()));
        }
        return body;
    }

    protected String post(String path, Object object) {
        URI uri = URI.create(getUriPrefix() + path);
        System.err.println(">>> POST " + uri);
        String json = gson.toJson(object);
        System.err.println(">>> " + json);
        HttpRequest request = HttpRequest.newBuilder()
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .build();
        final String body;
        try {
            body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        JsonElement jsonElement = JsonParser.parseString(body);
        if (jsonElement.isJsonObject()) {
            System.err.println("<<< " + gson.toJson(jsonElement.getAsJsonObject()));
        } else if (jsonElement.isJsonArray()) {
            System.err.println("<<< " + gson.toJson(jsonElement.getAsJsonArray()));
        }
        return body;
    }
}
