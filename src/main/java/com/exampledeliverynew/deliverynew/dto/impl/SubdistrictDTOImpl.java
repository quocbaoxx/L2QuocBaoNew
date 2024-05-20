package com.exampledeliverynew.deliverynew.dto.impl;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;

public class SubdistrictDTOImpl extends  LocationDtoImpl implements SubdistrictDTO {
    public SubdistrictDTOImpl(LocationResult locationResult ){
        super(locationResult);
    }
}
