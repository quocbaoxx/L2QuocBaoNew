package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.dto.impl.ProvinceDTOImpl;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import com.exampledeliverynew.deliverynew.entity.WareHouse;
import com.exampledeliverynew.deliverynew.repository.*;
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
    private  final WarehouseRepository warehouseRepository;
    private  final PartnerRepository partnerRepository;

    @Override
    public List<ProvinceDTO> getAllLever(Long leverMapping) {

        List<LocationResult> locationResults ;
        switch (leverMapping.intValue()){
            case  LEVER_ONE:
                locationResults = provinceRepository.getProvinceAndLogisticLevelOne();
                break;
            case  LEVER_TWO:
                locationResults = provinceRepository.getProvinceAndLogisticLevelTwo();
                break;
            case  LEVER_THREE:
                locationResults = provinceRepository.getProvinceAndLogisticLevelThree();
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
            validateEntities(updateDelivery);
        }else {
            throw  new IndexOutOfBoundsException(ErrorMessages.FORBIDDEN.getMessage());
        }
        defaultDelivery = new DefaultDelivery(updateDelivery.getLocationId(),updateDelivery.getFfmId(),updateDelivery.getLmId(),updateDelivery.getWhId());

        return new UpdateDelivery(defaultDelivery);
    }

    private void validateEntities(UpdateDelivery updateDelivery) {
        Long ffmId = updateDelivery.getFfmId();
        Long lmId = updateDelivery.getLmId();
        Long whId = updateDelivery.getWhId();

        WareHouse wareHouse = warehouseRepository.getById(whId);
        Long count = partnerRepository.checkExits(ffmId, lmId);

        if (wareHouse == null ||  count == null || !count.equals(2l) ) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }
        provinceRepository.updateDeliveryProvince(updateDelivery.getLocationId(), updateDelivery.getFfmId(), updateDelivery.getLmId(), updateDelivery.getWhId());
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
