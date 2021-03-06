package com.leeym.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.leeym.common.ApiToken;
import com.leeym.common.BaseUrl;
import com.leeym.common.CurrencyTypeAdapter;
import com.leeym.common.JavaTimeTypeAdapterFactory;
import com.leeym.common.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Currency;
import java.util.UUID;
import java.util.logging.Logger;

public class BaseApi {
    protected final Gson gson;
    private final Logger logger = Logger.getGlobal();
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
                .registerTypeAdapter(Currency.class, new CurrencyTypeAdapter())
                .registerTypeAdapterFactory(new JavaTimeTypeAdapterFactory())
                .setPrettyPrinting()
                .create();
    }

    protected String get(String path) {
        URI uri = URI.create(baseUrl + path);
        logger.info(">>> GET " + uri);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .build();
        return send(request);
    }

    protected String post(String path, Object object) {
        URI uri = URI.create(baseUrl + path);
        String json = gson.toJson(object);
        logger.info(">>> POST " + uri + "\n" + json);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("X-idempotence-uuid", UUID.randomUUID().toString())
                .build();
        return send(request);
    }

    protected String send(HttpRequest request) {
        final long start = System.currentTimeMillis();
        try {
            String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            final long end = System.currentTimeMillis();
            JsonElement jsonElement = JsonParser.parseString(body);
            if (jsonElement.isJsonObject()) {
                logger.info("<<< " + (end - start) + " ms\n" + gson.toJson(jsonElement.getAsJsonObject()));
            } else if (jsonElement.isJsonArray()) {
                logger.info("<<< " + (end - start) + " ms\n" + gson.toJson(jsonElement.getAsJsonArray()));
            }
            return body;
        } catch (IOException | InterruptedException e) {
            logger.info((System.currentTimeMillis() - start) + " ms");
            throw new RuntimeException(e);
        }
    }
}
