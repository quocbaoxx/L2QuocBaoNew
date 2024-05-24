package com.exampledeliverynew.deliverynew.controller;

import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private  final DeliveryService deliveryService;

    @GetMapping("/logistics")
    public DataResponse<List<LocationResult>> getAllLogistics(){
        List<LocationResult> locationResults = deliveryService.getAllLogistics();
        return DataResponse.ok(locationResults);
    }


}
