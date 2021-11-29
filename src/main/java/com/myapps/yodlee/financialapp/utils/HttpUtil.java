package com.myapps.yodlee.financialapp.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Utilities class for HTTP requests. Currently only GET requests are addressed as utility method.

public class HttpUtil {

    public static HttpResponse<String> sendGetRequest(String token, String endpoint) throws IOException, InterruptedException, URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(Constants.API_URL + endpoint))
                .header("Api-Version", Constants.API_VERSION)
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
