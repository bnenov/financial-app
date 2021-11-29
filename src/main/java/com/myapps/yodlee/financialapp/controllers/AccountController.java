package com.myapps.yodlee.financialapp.controllers;

import com.myapps.yodlee.financialapp.config.GlobalProperties;
import com.myapps.yodlee.financialapp.model.view.*;
import com.myapps.yodlee.financialapp.services.AccountService;
import com.myapps.yodlee.financialapp.model.service.HoldingServiceModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* Controller class which is used for getting all needed account related information:
    - providers, accounts, balances, holdings, etc.
* */

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final GlobalProperties globalProperties;
    private final ModelMapper modelMapper;

    public AccountController(AccountService accountService, GlobalProperties globalProperties, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.globalProperties = globalProperties;
        this.modelMapper = modelMapper;
    }

    // This method gets all providers for which the user has accounts and all account related information

    @GetMapping
    public String getAllAccounts(Model model) {

        String token = globalProperties.getProperties().get("token");


        // Get all available accounts
        List<AccountViewModel> accountViews = modelMapper.map(accountService.getAllAccounts(token),
                new TypeToken<List<AccountViewModel>>() {
                }.getType());

        // If no accounts then return to view as there is nothing to extract
        if (accountViews.size() == 0) {

            model.addAttribute("hasAccounts", false);
            return "accounts";

        } else {
            model.addAttribute("hasAccounts", true);
        }


        // Get all holdings per each account
        for (AccountViewModel accountViewModel : accountViews) {

            List<HoldingServiceModel> holdingServiceModels =
                    accountService.getHoldingsPerAccount(token, accountViewModel.getId());

            // Make sure to set empty list for the accounts' holdings to avoid null exceptions
            if (holdingServiceModels != null) {

                List<HoldingViewModel> holdingViews =
                        modelMapper.map(holdingServiceModels,
                                new TypeToken<List<HoldingViewModel>>() {
                                }.getType());

                accountViewModel.setHoldings(holdingViews);
            } else {
                accountViewModel.setHoldings(new ArrayList<>());
            }

        }

        UserDataViewModel userDataViewModel = new UserDataViewModel();
        userDataViewModel.setProviders(new ArrayList<>());

        // Get distinct provider ids
        Set<Integer> setProviderId = new HashSet<>();
        accountViews.forEach(a -> setProviderId.add(a.getProviderId()));

        // Get the distinct provider accounts for each provider
        for (int providerId : setProviderId) {
            Set<Integer> setProviderAccountId = new HashSet<>();

            // Get distinct provider accounts into Set
            accountViews.stream().filter(a -> a.getProviderId() == providerId)
                    .forEach(a -> setProviderAccountId.add(a.getProviderAccountId()));

            // ToDo: Remove the print line. It is currently kept to see the distinct provider accounts
            //  for testing purposes
            System.out.println(setProviderAccountId.toString());

            // Prepare the list of provider account objects
            List<ProviderAccountViewModel> providerAccountsList = new ArrayList<>();

            setProviderAccountId.forEach(pa -> {
                ProviderAccountViewModel providerAccountViewModel = new ProviderAccountViewModel();
                providerAccountViewModel.setId(pa);
                providerAccountsList.add(providerAccountViewModel);
            });

            // Link the provider accounts to their related accounts
            for (ProviderAccountViewModel providerAccountViewModel : providerAccountsList) {

                providerAccountViewModel.setAccounts(
                        accountViews.stream().filter(a -> a.getProviderAccountId() ==
                                providerAccountViewModel.getId()).collect(Collectors.toList()));
            }

            // Link the providers to the provider accounts
            ProviderViewModel provider = new ProviderViewModel();
            provider.setId(providerId);
            provider.setName(providerAccountsList.get(0).getAccounts().get(0).getProviderName());
            provider.setProviderAccounts(providerAccountsList);

            // Store all information into the UserData view object
            userDataViewModel.getProviders().add(provider);
        }

        model.addAttribute("userData", userDataViewModel);

        return "accounts";
    }
}
