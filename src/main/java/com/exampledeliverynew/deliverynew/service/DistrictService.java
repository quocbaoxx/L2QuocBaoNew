package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;

import java.util.List;

public interface DistrictService {
    List<DistrictDTO> getAllLeverDistrict(Long lever);

    UpdateDelivery updateDeliveryDistrict(int lever, UpdateDelivery updateDelivery);
}
