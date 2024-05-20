package com.exampledeliverynew.deliverynew.dto.impl;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;

public class ProvinceDTOImpl extends  LocationDtoImpl implements ProvinceDTO {
    public ProvinceDTOImpl(LocationResult locationResult) {
        super(locationResult);
    }
}
