package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import com.exampledeliverynew.deliverynew.repository.DeliveryRepository;
import com.exampledeliverynew.deliverynew.repository.DistrictRepository;
import com.exampledeliverynew.deliverynew.repository.ProvinceRepository;
import com.exampledeliverynew.deliverynew.repository.SubdistrictRepository;
import com.exampledeliverynew.deliverynew.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private  final DeliveryRepository deliveryRepository;
    private  final ProvinceRepository provinceRepository;
    private  final DistrictRepository districtRepository;
    private  final SubdistrictRepository subdistrictRepository;

    @Override
    public List<LocationResult> getAllLogitict() {
        List<LocationResult> locationResults = deliveryRepository.getAllLogitict();
        return locationResults;
    }

    @Override
    public UpdateDelivery updateDelivery(int lever, UpdateDelivery updateDelivery) {

        DefaultDelivery defaultDelivery;
        Long locationIdDTO = updateDelivery.getLocationId();
        int locationLv ;
        if (provinceRepository.existsById(locationIdDTO)){
            locationLv = 1;
        }else  if (districtRepository.existsById(locationIdDTO)){
            locationLv = 2;
        }else if (subdistrictRepository.existsById(locationIdDTO)){
            locationLv = 3;
        }else {
            throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }

        if (locationLv >= lever){
//            e dùng sql native mà nó báo lỗi Executing an update/delete query e chưa bug đc
//            deliveryRepository.updateDelivery(updateDelivery.getLocationId(), updateDelivery.getFfmId(), updateDelivery.getLmId(), updateDelivery.getWhId());
            defaultDelivery = deliveryRepository.findUpdatedDelivery(updateDelivery.getLocationId());
            if (defaultDelivery == null) {
                throw new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
            }
            defaultDelivery.setFfmId(updateDelivery.getFfmId());
            defaultDelivery.setLmId(updateDelivery.getLmId());
            defaultDelivery.setWarehouseId(updateDelivery.getWhId());
            deliveryRepository.save(defaultDelivery);
        }else {
            throw  new IndexOutOfBoundsException(ErrorMessages.FORBIDDEN.getMessage());
        }

        return new UpdateDelivery(defaultDelivery);
    }
}
