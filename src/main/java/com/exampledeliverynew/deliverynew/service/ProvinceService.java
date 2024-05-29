package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateLogistics;

import java.util.List;

public interface ProvinceService {
    List<ProvinceDTO> getAllLever(Long leverMapping);

    UpdateLogistics updateDeliveryProvince(int lever, UpdateLogistics updateLogistics);
}
