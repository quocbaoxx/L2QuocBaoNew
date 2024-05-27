package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;

import java.util.List;

public interface ProvinceService {
    List<ProvinceDTO> getAllLever(Long leverMapping);

    UpdateDelivery updateDeliveryProvince(int lever, UpdateDelivery updateDelivery);
}
