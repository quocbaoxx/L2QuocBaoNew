package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.config.LocationLeverProperties;
import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.UpdateLogistics;
import com.exampledeliverynew.deliverynew.dto.impl.DistrictDTOImpl;
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
    private  final WarehouseRepository warehouseRepository;
    private  final PartnerRepository  partnerRepository;
    private final LocationLeverProperties locationLeverProperties;


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
    public UpdateLogistics updateDeliveryDistrict(int lever, UpdateLogistics updateLogistics) {
        Long  count  = districtRepository.checkExitsDistrict(updateLogistics.getId());
        if (count.equals(0l)){
            throw   new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }
        switch (lever){
            case LEVER_ONE:
                if (locationLeverProperties.getLocationLever() <= lever){
                    validateEntities(updateLogistics);
                 districtRepository.updateDeliveryDistrictLv1(updateLogistics.getId(), updateLogistics.getFfmId(), updateLogistics.getLmId(), updateLogistics.getWhId());
                }else {
                    throw  new IllegalArgumentException(ErrorMessages.FORBIDDEN.getMessage());
                }
                break;
            case LEVER_TWO:
                if (locationLeverProperties.getLocationLever() <= lever){
                    validateEntities(updateLogistics);
                    districtRepository.updateDeliveryDistrictLv2(updateLogistics.getId(), updateLogistics.getFfmId(), updateLogistics.getLmId(), updateLogistics.getWhId());
                }else {
                    throw  new IllegalArgumentException(ErrorMessages.FORBIDDEN.getMessage());
                }
                break;
            case LEVER_THREE:
                validateEntities(updateLogistics);
                districtRepository.updateDeliveryDistrictLv3(updateLogistics.getId(), updateLogistics.getFfmId(), updateLogistics.getLmId(), updateLogistics.getWhId());
                break;
            default:
                throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());

        }
        return  updateLogistics;
    }


    private void validateEntities(UpdateLogistics updateLogistics) {
        Long ffmId = updateLogistics.getFfmId();
        Long lmId = updateLogistics.getLmId();
        Long whId = updateLogistics.getWhId();

        WareHouse wareHouse = warehouseRepository.getById(whId);
        Long count = partnerRepository.checkExits(ffmId, lmId);

        if (wareHouse == null ||  count == null || !count.equals(2l) ) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }
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
