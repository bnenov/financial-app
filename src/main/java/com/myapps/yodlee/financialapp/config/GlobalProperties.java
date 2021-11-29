package com.myapps.yodlee.financialapp.config;

import org.springframework.stereotype.Component;

import java.util.Map;

// This class is used to store any property which will be needed across the app.
// Currently it stores only the authorization token for the specific user

@Component
public class GlobalProperties {

    private Map<String, String> properties;

    public GlobalProperties() {
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
