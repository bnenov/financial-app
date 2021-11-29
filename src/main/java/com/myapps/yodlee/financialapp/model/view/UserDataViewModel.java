package com.myapps.yodlee.financialapp.model.view;

import java.util.List;

public class UserDataViewModel {

    private List<ProviderViewModel> providers;

    public UserDataViewModel() {
    }

    public List<ProviderViewModel> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderViewModel> providers) {
        this.providers = providers;
    }

}
