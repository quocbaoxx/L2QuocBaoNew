package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;

import java.util.List;

public interface DeliveryService {
    List<LocationResult> getAllLogitict();

    UpdateDelivery updateDelivery(int lever, UpdateDelivery updateDelivery);
}
