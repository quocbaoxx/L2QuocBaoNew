package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.repository.DeliveryRepository;
import com.exampledeliverynew.deliverynew.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.exampledeliverynew.deliverynew.consts.Const.*;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private  final DeliveryRepository deliveryRepository;

    @Override
    public List<LocationResult> getAllLogistics() {
        List<LocationResult> locationResults = deliveryRepository.getAllLogistics();
        return locationResults;
    }

    @Override
    public List<LocationResult> getAllExpost(int lever) {
        switch (lever) {
            case LEVER_ONE:
                return deliveryRepository.getProvinceInformation();
            case LEVER_TWO:
                return deliveryRepository.getDistrictInformation();
            case LEVER_THREE:
                return deliveryRepository.getSubdistrictInformation();
            default:
                throw new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }
    }








}
