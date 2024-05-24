package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;

import java.util.List;

public interface SubdistrictService {
    List<SubdistrictDTO> getAllLeverSubdistrict(Long lever);

    UpdateDelivery updateDeliverySubdistrict(int lever, UpdateDelivery updateDelivery);
}
