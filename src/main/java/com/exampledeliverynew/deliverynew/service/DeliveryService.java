package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.LocationResult;

import java.util.List;

public interface DeliveryService {
    List<LocationResult> getAllLogistics();

    List<LocationResult> getAllExpost(int lever);
}
