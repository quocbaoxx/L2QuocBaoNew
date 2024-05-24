package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.repository.DeliveryRepository;
import com.exampledeliverynew.deliverynew.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private  final DeliveryRepository deliveryRepository;

    @Override
    public List<LocationResult> getAllLogistics() {
        List<LocationResult> locationResults = deliveryRepository.getAllLogistics();
        return locationResults;
    }











}
