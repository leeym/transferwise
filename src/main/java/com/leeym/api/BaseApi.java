package com.leeym.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;
import com.leeym.common.JavaTimeTypeAdapterFactory;
import com.leeym.common.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BaseApi {
    protected final Gson gson;
    private final BaseUrl baseUrl;
    private final ApiToken token;
    private final HttpClient client;

    public BaseApi(BaseUrl baseUrl, ApiToken token) {
        if (!baseUrl.getStage().equals(token.getStage())) {
            throw new IllegalArgumentException(baseUrl.getClass().getSimpleName() + ".stage [" + baseUrl.getStage() +
                    "] doesn't match " + token.getClass().getSimpleName() + ".stage [" + token.getStage() + "]");
        }
        if (baseUrl.getStage() == Stage.LIVE) {
            throw new UnsupportedOperationException("Not ready to hit " + baseUrl);
        }
        this.baseUrl = baseUrl;
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        this.token = token;
        this.gson = new GsonBuilder()
                .registerTypeAdapterFactory(new JavaTimeTypeAdapterFactory())
                .setPrettyPrinting()
                .create();
    }

    protected String get(String path) {
        URI uri = URI.create(baseUrl + path);
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
        URI uri = URI.create(baseUrl + path);
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
