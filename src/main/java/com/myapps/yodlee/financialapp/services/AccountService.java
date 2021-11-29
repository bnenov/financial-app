package com.myapps.yodlee.financialapp.services;

import com.myapps.yodlee.financialapp.model.service.AccountServiceModel;
import com.myapps.yodlee.financialapp.model.service.HoldingServiceModel;
import com.myapps.yodlee.financialapp.model.service.TokenServiceModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

// Service interface for operating with Yodlee's account related endpoints

public interface AccountService {

    List<AccountServiceModel> getAllAccounts(String token);

    List<HoldingServiceModel> getHoldingsPerAccount(String token, int accountId);

    TokenServiceModel getToken(String url) throws URISyntaxException, IOException, InterruptedException;

}
