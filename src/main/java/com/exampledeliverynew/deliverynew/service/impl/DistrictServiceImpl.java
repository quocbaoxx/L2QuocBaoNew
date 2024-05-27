package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.dto.impl.DistrictDTOImpl;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import com.exampledeliverynew.deliverynew.entity.WareHouse;
import com.exampledeliverynew.deliverynew.repository.*;
import com.exampledeliverynew.deliverynew.service.DistrictService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.exampledeliverynew.deliverynew.consts.Const.*;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private  final DistrictRepository districtRepository;
    private  final ProvinceRepository provinceRepository;
    private  final SubdistrictRepository subdistrictRepository;
    private  final WarehouseRepository warehouseRepository;
    private  final PartnerRepository  partnerRepository;

    @Override
    public List<DistrictDTO> getAllLeverDistrict(Long lever,Long provinceId) {
        List<LocationResult> locationResults ;
        switch (lever.intValue()){
            case  LEVER_ONE:
                locationResults = districtRepository.getDistrictAndLogisticLevelOne(provinceId);
                break;
            case  LEVER_TWO:
                locationResults = districtRepository.getDistrictAndLogisticLevelTwo(provinceId);
                break;
            case  LEVER_THREE:
                locationResults = districtRepository.getDistrictAndLogisticLevelThree(provinceId);
                break;
            default:
                throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());

        }
        return  mapQueryResultsToDTO(locationResults);
    }

    @Override
    @Transactional
    public UpdateDelivery updateDeliveryDistrict(int lever, UpdateDelivery updateDelivery) {
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
        districtRepository.updateDeliveryDistrict(updateDelivery.getLocationId(), updateDelivery.getFfmId(), updateDelivery.getLmId(), updateDelivery.getWhId());
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
