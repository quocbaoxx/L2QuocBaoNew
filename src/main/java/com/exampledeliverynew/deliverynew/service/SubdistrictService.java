package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;

import java.util.List;

public interface SubdistrictService {
    List<SubdistrictDTO> getAllLeverSubdistrict(Long lever, Long districtId);

    UpdateDelivery updateDeliverySubdistrict(int lever, UpdateDelivery updateDelivery);
}
