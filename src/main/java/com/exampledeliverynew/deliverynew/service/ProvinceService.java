package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;

import java.util.List;

public interface ProvinceService {
    List<ProvinceDTO> getAllLever(Long leverMapping);

    List<LocationResult> getAllExpost(int level);

    UpdateDelivery updateDeliveryProvince(int lever, UpdateDelivery updateDelivery);
}
