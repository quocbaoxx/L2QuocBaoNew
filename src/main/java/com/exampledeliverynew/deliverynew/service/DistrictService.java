package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.DistrictDTO;

import java.util.List;

public interface DistrictService {
    List<DistrictDTO> getAllLeverDistrict(Long lever);
}
