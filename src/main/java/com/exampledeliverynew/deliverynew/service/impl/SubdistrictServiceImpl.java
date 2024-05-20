package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.dto.impl.SubdistrictDTOImpl;
import com.exampledeliverynew.deliverynew.repository.SubdistrictRepository;
import com.exampledeliverynew.deliverynew.service.SubdistrictService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.exampledeliverynew.deliverynew.consts.Const.*;


@Service
@AllArgsConstructor
public class SubdistrictServiceImpl implements SubdistrictService {

    private  final SubdistrictRepository subdistrictRepository;

    @Override
    public List<SubdistrictDTO> getAllLeverSubdistrict(Long lever) {

        List<LocationResult> locationResults ;
        switch (lever.intValue()){
            case  LEVER_SUBDISTRICT:{
                locationResults = subdistrictRepository.getLogiticSubdistrict();
                break;
            }
            default:
                throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }
        return  mapQueryResultsToDTO(locationResults);
    }
    private List<SubdistrictDTO> mapQueryResultsToDTO(List<LocationResult> locationResults) {
        Map<Long, SubdistrictDTO> subdistrictDTOMap = new HashMap<>();
        for (LocationResult locationResult : locationResults) {
            Long locationId = locationResult.getLocationId();
            SubdistrictDTO subdistrictDTO = subdistrictDTOMap.get(locationId);
            if (subdistrictDTO == null) {
                subdistrictDTO = new SubdistrictDTOImpl(locationResult);
                subdistrictDTOMap.put(locationId, subdistrictDTO);
            }
            subdistrictDTO.addFulfilmentDTO(locationResult);
            subdistrictDTO.addLastmileDTO(locationResult);
            subdistrictDTO.addWarehouse(locationResult);
        }
        return new LinkedList<>(subdistrictDTOMap.values());
    }
}
