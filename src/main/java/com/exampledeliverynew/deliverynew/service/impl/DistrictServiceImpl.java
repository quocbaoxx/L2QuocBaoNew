package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.impl.DistrictDTOImpl;
import com.exampledeliverynew.deliverynew.dto.impl.ProvinceDTOImpl;
import com.exampledeliverynew.deliverynew.repository.DistrictRepository;
import com.exampledeliverynew.deliverynew.service.DistrictService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.exampledeliverynew.deliverynew.consts.Const.*;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private  final DistrictRepository districtRepository;

    @Override
    public List<DistrictDTO> getAllLeverDistrict(Long lever) {
        List<LocationResult> locationResults ;
        switch (lever.intValue()){
            case  LEVER_PROVINCE:
                locationResults = districtRepository.getLogisticsProvince();
                break;
            case  LEVER_DISTRICT:
                locationResults = districtRepository.getLogisticDistricts();
                break;
            case  LEVER_SUBDISTRICT:
                locationResults = districtRepository.getLogisticsSubdistricts();
                break;
            default:
                throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());

        }
        return  mapQueryResultsToDTO(locationResults);
    }
    private List<DistrictDTO> mapQueryResultsToDTO(List<LocationResult> locationResults) {
        Map<Long, DistrictDTO> districtMap = new HashMap<>();
        for (LocationResult locationResult : locationResults) {
            Long locationId = locationResult.getLocationId();
            DistrictDTO districtDTO = districtMap.get(locationId);
            if (districtDTO == null) {
                districtDTO = new DistrictDTOImpl(locationResult);
                districtMap.put(locationId, districtDTO);
            }
            districtDTO.addFulfilmentDTO(locationResult);
            districtDTO.addLastmileDTO(locationResult);
            districtDTO.addWarehouse(locationResult);
        }
        return new LinkedList<>(districtMap.values());
    }
}
