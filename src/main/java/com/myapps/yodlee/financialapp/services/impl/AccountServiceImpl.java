package com.myapps.yodlee.financialapp.services.impl;

import com.google.gson.Gson;
import com.myapps.yodlee.financialapp.model.beans.*;
import com.myapps.yodlee.financialapp.services.AccountService;
import com.myapps.yodlee.financialapp.model.service.AccountServiceModel;
import com.myapps.yodlee.financialapp.model.service.HoldingServiceModel;
import com.myapps.yodlee.financialapp.model.service.TokenResourceServiceModel;
import com.myapps.yodlee.financialapp.model.service.TokenServiceModel;
import com.myapps.yodlee.financialapp.utils.Constants;
import com.myapps.yodlee.financialapp.utils.HttpUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Service class for operating with Yodlee's account related endpoints

@Service
public class AccountServiceImpl implements AccountService {

    private final Gson gson;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(Gson gson, ModelMapper modelMapper) {
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    // This method gets all accounts for the currently authenticated user.

    @Override
    public List<AccountServiceModel> getAllAccounts(String token) {

        try {

            String endpoint = "/accounts";
            String responseBody = HttpUtil.sendGetRequest(token, endpoint).body();

            AccountsArray accounts = gson.fromJson(responseBody, AccountsArray.class);

            if(accounts.getAccount() == null) {
                return new ArrayList<>();
            }

            List<Account> accountsList = Arrays.asList(accounts.getAccount());


            return modelMapper.map(accountsList, new TypeToken<List<AccountServiceModel>>() {
            }.getType());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return new ArrayList<>();
    }

    // This method gets all holdings (asset info) for the specific account

    @Override
    public List<HoldingServiceModel> getHoldingsPerAccount(String token, int accountId) {

        try {

            String endpoint = "/holdings?accountId=" + accountId;
            String responseBody = HttpUtil.sendGetRequest(token, endpoint).body();

            HoldingsArray holdingsArray = gson.fromJson(responseBody, HoldingsArray.class);

            if(holdingsArray.getHolding() == null) {
                return new ArrayList<>();
            }

            List<Holding> holdings = Arrays.asList(holdingsArray.getHolding());

            return modelMapper.map(holdings, new TypeToken<List<HoldingServiceModel>>() {
            }.getType());


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>();
    }


    // This method sends a POST request to generate an authorization token for the specific user.
    // This is the first method that should be executed before any other requests are sent to Yodlee.
    // Currently this method is called at app startup in the ApplicationStartup class

    public TokenServiceModel getToken(String url) throws URISyntaxException, IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url + "/auth/token"))
                .header("Api-Version", Constants.API_VERSION)
                .header("loginName", Constants.LOGIN_USERNAME)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("clientId=" + Constants.CLIENT_ID + "&secret=" + Constants.CLIENT_SECRET))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        TokenResource tokenResource = gson.fromJson(response.body(), TokenResource.class);
        TokenResourceServiceModel tokenResourceServiceModel = modelMapper.map(tokenResource, TokenResourceServiceModel.class);

        return tokenResourceServiceModel.getToken();
    }
}
