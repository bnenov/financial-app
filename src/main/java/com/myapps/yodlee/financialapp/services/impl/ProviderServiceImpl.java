package com.myapps.yodlee.financialapp.services.impl;

import com.google.gson.Gson;
import com.myapps.yodlee.financialapp.services.ProviderService;
import com.myapps.yodlee.financialapp.model.beans.Provider;
import com.myapps.yodlee.financialapp.model.beans.ProvidersArray;
import com.myapps.yodlee.financialapp.model.service.ProviderServiceModel;
import com.myapps.yodlee.financialapp.utils.HttpUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Service class for operating with Yodlee's provider endpoints

@Service
public class ProviderServiceImpl implements ProviderService {

    private final Gson gson;
    private final ModelMapper modelMapper;

    public ProviderServiceImpl(Gson gson, ModelMapper modelMapper) {
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    // This method gets all available providers. For the sandbox users it gets only a handful of results.

    @Override
    public List<ProviderServiceModel> getAllProviders(String token) {

        try {

            String endpoint = "/providers";
            String responseBody = HttpUtil.sendGetRequest(token, endpoint).body();

            ProvidersArray providersArray = gson.fromJson(responseBody, ProvidersArray.class);

            List<Provider> providers = Arrays.asList(providersArray.getProvider());

            return modelMapper.map(providers, new TypeToken<List<ProviderServiceModel>>() {
            }.getType());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>();
    }

}
