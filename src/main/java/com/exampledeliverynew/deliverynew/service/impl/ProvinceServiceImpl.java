package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.dto.impl.ProvinceDTOImpl;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import com.exampledeliverynew.deliverynew.repository.DistrictRepository;
import com.exampledeliverynew.deliverynew.repository.ProvinceRepository;
import com.exampledeliverynew.deliverynew.repository.SubdistrictRepository;
import com.exampledeliverynew.deliverynew.service.ProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.exampledeliverynew.deliverynew.consts.Const.*;


@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository ;
    private  final DistrictRepository districtRepository;
    private  final SubdistrictRepository subdistrictRepository;

    @Override
    public List<ProvinceDTO> getAllLever(Long leverMapping) {

        List<LocationResult> locationResults ;
        switch (leverMapping.intValue()){
            case  LEVER_PROVINCE:
                locationResults = provinceRepository.getDistrictAndLogisticLevelOne();
                break;
            case  LEVER_DISTRICT:
                locationResults = provinceRepository.getDistrictAndLogisticLevelTwo();
                break;
            case  LEVER_SUBDISTRICT:
                locationResults = provinceRepository.getDistrictAndLogisticLevelThree();
                break;
            default:
                throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());

        }
        return  mapQueryResultsToDTO(locationResults);
    }


    @Override
    @Transactional
    public UpdateDelivery updateDeliveryProvince(int lever, UpdateDelivery updateDelivery) {

        DefaultDelivery defaultDelivery ;
        Long locationIdDTO = updateDelivery.getLocationId();
        int locationLv ;
        if (provinceRepository.existsById(locationIdDTO)){
            locationLv = 1;
        }else  if (districtRepository.existsById(locationIdDTO)){
            locationLv = 2;
        }else if (subdistrictRepository.existsById(locationIdDTO)){
            locationLv = 3;
        }else {
            throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }

        if (locationLv >= lever){
            provinceRepository.updateDeliveryProvince(updateDelivery.getLocationId(), updateDelivery.getFfmId(), updateDelivery.getLmId(), updateDelivery.getWhId());
        }else {
            throw  new IndexOutOfBoundsException(ErrorMessages.FORBIDDEN.getMessage());
        }
        defaultDelivery = new DefaultDelivery(updateDelivery.getLocationId(),updateDelivery.getFfmId(),updateDelivery.getLmId(),updateDelivery.getWhId());

        return new UpdateDelivery(defaultDelivery);
    }

    @Override
    public List<LocationResult> getAllExpost(int lever) {
        switch (lever) {
            case LEVER_PROVINCE:
                return provinceRepository.getDistrictAndLogisticLevelOne();
            case LEVER_DISTRICT:
                return provinceRepository.getDistrictAndLogisticLevelTwo();
            case LEVER_SUBDISTRICT:
                return provinceRepository.getDistrictAndLogisticLevelThree();
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
