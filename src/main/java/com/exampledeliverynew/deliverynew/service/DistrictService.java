package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateLogistics;

import java.util.List;

public interface DistrictService {
    List<DistrictDTO> getAllLeverDistrict(Long lever, Long provinceId);

    UpdateLogistics updateDeliveryDistrict(int lever, UpdateLogistics updateLogistics);
}
