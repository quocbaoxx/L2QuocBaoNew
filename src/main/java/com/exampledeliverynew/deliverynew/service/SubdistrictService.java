package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateLogistics;

import java.util.List;

public interface SubdistrictService {
    List<SubdistrictDTO> getAllLeverSubdistrict(Long lever, Long districtId);

    UpdateLogistics updateDeliverySubdistrict(int lever, UpdateLogistics updateLogistics);
}
