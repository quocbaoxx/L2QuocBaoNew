package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;

import java.util.List;

public interface DeliveryService {
    List<LocationResult> getAllLogistics();

    UpdateDelivery updateDelivery(int lever, UpdateDelivery updateDelivery);
}
