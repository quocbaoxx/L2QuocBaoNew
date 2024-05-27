package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.dto.impl.SubdistrictDTOImpl;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import com.exampledeliverynew.deliverynew.entity.WareHouse;
import com.exampledeliverynew.deliverynew.repository.*;
import com.exampledeliverynew.deliverynew.service.SubdistrictService;
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
public class SubdistrictServiceImpl implements SubdistrictService {

    private  final SubdistrictRepository subdistrictRepository;
    private  final DistrictRepository districtRepository;
    private  final ProvinceRepository provinceRepository;
    private  final PartnerRepository partnerRepository;
    private  final WarehouseRepository warehouseRepository;


    @Override
    public List<SubdistrictDTO> getAllLeverSubdistrict(Long lever, Long districtId) {

        List<LocationResult> locationResults ;
        switch (lever.intValue()){
            case  LEVER_ONE:
                locationResults = subdistrictRepository.getSubDistrictAndLogisticLevelOne(districtId);
                break;
            case LEVER_TWO:
                locationResults = subdistrictRepository.getSubDistrictAndLogisticLevelTwo(districtId);
                break;
            case  LEVER_THREE:
                locationResults = subdistrictRepository.getSubDistrictAndLogisticLevelThree(districtId);
                break;
            default:
                throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }
        return  mapQueryResultsToDTO(locationResults);
    }

    @Override
    @Transactional
    public UpdateDelivery updateDeliverySubdistrict(int lever, UpdateDelivery updateDelivery) {
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
        subdistrictRepository.updateDeliveryDistrict(updateDelivery.getLocationId(), updateDelivery.getFfmId(), updateDelivery.getLmId(), updateDelivery.getWhId());
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
