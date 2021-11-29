package com.myapps.yodlee.financialapp.services;

import com.myapps.yodlee.financialapp.model.service.ProviderServiceModel;

import java.util.List;

// Service interface for operating with Yodlee's provider endpoints

public interface ProviderService {

    List<ProviderServiceModel> getAllProviders(String token);

}
