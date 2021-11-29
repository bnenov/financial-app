package com.myapps.yodlee.financialapp.controllers;

import com.myapps.yodlee.financialapp.config.GlobalProperties;
import com.myapps.yodlee.financialapp.model.service.ProviderServiceModel;
import com.myapps.yodlee.financialapp.model.view.ProviderViewModel;
import com.myapps.yodlee.financialapp.services.ProviderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/providers")
public class ProviderController {

    private final GlobalProperties globalProperties;
    private final ModelMapper modelMapper;
    private final ProviderService providerService;

    public ProviderController(GlobalProperties globalProperties, ModelMapper modelMapper, ProviderService providerService) {
        this.globalProperties = globalProperties;
        this.modelMapper = modelMapper;
        this.providerService = providerService;
    }

    @GetMapping
    public String getProviders(Model model) {

        List<ProviderServiceModel> listProviders =
                providerService.getAllProviders(globalProperties.getProperties().get("token"));

        List<ProviderViewModel> providersView =
                modelMapper.map(listProviders, new TypeToken<List<ProviderViewModel>>() {
                }.getType());

        model.addAttribute("providers", providersView);

        return "account";
    }
}
