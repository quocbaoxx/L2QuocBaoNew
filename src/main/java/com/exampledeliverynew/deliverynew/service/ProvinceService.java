package com.exampledeliverynew.deliverynew.service;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;

import java.util.List;

public interface ProvinceService {
    List<ProvinceDTO> getAllLever(Long leverMapping);

    List<LocationResult> getAllExpost(int level);

}
