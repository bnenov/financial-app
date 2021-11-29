package com.myapps.yodlee.financialapp.init;

import com.myapps.yodlee.financialapp.config.GlobalProperties;
import com.myapps.yodlee.financialapp.model.service.TokenServiceModel;
import com.myapps.yodlee.financialapp.services.AccountService;
import com.myapps.yodlee.financialapp.utils.Constants;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

// This class is used for startup initialization.
// Currently during app startup this class calls the getToken() method of AccountService to generate a token
// for the user and then sets this token value as a global property which will be used across the app.

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final AccountService accountService;
    private final GlobalProperties globalProperties;

    public ApplicationStartup(AccountService accountService, GlobalProperties globalProperties) {
        this.accountService = accountService;
        this.globalProperties = globalProperties;
    }

    // Sends a GET request to Yodlee for a new token and stores it as a global property.

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {

            TokenServiceModel tokenServiceModel = accountService.getToken(Constants.API_URL);
            Map<String, String> mapProperties = new HashMap<>();
            mapProperties.put("token", tokenServiceModel.getAccessToken());

            // ToDo: Remove print of token. Currently used for testing purposes.
            System.out.println(mapProperties.get("token"));

            globalProperties.setProperties(mapProperties);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
