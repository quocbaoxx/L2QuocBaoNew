package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.impl.ProvinceDTOImpl;
import com.exampledeliverynew.deliverynew.repository.ProvinceRepository;
import com.exampledeliverynew.deliverynew.service.ProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.exampledeliverynew.deliverynew.consts.Const.*;


@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository ;

    @Override
    public List<ProvinceDTO> getAllLever(Long leverMapping) {

        List<LocationResult> locationResults ;
        switch (leverMapping.intValue()){
            case  LEVER_PROVINCE:{
                locationResults = provinceRepository.getLogiticProvince();
                break;
            }
            case  LEVER_DISTRICT:{
                locationResults = provinceRepository.getLogiticDistrict();
                break;
            }
            case  LEVER_SUBDISTRICT:{
                locationResults = provinceRepository.getLogiticSubdistrict();
                break;
            }
            default:
                throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());

        }
        return  mapQueryResultsToDTO(locationResults);
    }

    @Override
    public List<LocationResult> getAllexpost(int lever) {
        switch (lever) {
            case LEVER_PROVINCE: {
                return provinceRepository.getLogiticProvince();
            }
            case LEVER_DISTRICT: {
                return provinceRepository.getLogiticDistrict();
            }
            case LEVER_SUBDISTRICT: {
                return provinceRepository.getLogiticSubdistrict();
            }
            default:
                throw new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }
    }

    private List<ProvinceDTO> mapQueryResultsToDTO(List<LocationResult> locationResults) {
        Map<Long, ProvinceDTO> provinceMap = new HashMap<>();
        for (LocationResult locationResult : locationResults) {
            Long locationId = locationResult.getLocationId();
            ProvinceDTO provinceDTO = provinceMap.get(locationId);
            if (provinceDTO == null) {
                provinceDTO = new ProvinceDTOImpl(locationResult);
                provinceMap.put(locationId, provinceDTO);
            }
            provinceDTO.addFulfilmentDTO(locationResult);
            provinceDTO.addLastmileDTO(locationResult);
            provinceDTO.addWarehouse(locationResult);
        }
        return new LinkedList<>(provinceMap.values());
    }



}
