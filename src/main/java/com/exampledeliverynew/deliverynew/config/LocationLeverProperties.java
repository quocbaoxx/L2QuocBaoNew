package com.exampledeliverynew.deliverynew.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myapp")
public class LocationLeverProperties {
    private int locationLever;

    public int getLocationLever() {
        return locationLever;
    }

    public void setLocationLever(int locationLever) {
        this.locationLever = locationLever;
    }
}
