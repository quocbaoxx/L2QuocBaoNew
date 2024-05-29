package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.config.LocationLeverProperties;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateLogistics;
import com.exampledeliverynew.deliverynew.dto.impl.ProvinceDTOImpl;
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
    private  final WarehouseRepository warehouseRepository;
    private  final PartnerRepository partnerRepository;
    private final LocationLeverProperties locationLeverProperties;


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
    public UpdateLogistics updateDeliveryProvince(int lever, UpdateLogistics updateLogistics) {
        Long count =  provinceRepository.checkExitsProvince(updateLogistics.getId());
        if (count.equals(0l)){
            throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }
        switch (lever){
            case LEVER_ONE:
                if (locationLeverProperties.getLocationLever() <= lever){
                    validateEntities(updateLogistics);
                    provinceRepository.updateLogisticProvinceLv1(updateLogistics.getId(), updateLogistics.getFfmId(),updateLogistics.getLmId(),updateLogistics.getWhId());
                }else {
                    throw  new IllegalArgumentException(ErrorMessages.FORBIDDEN.getMessage());
                }
                break;
            case LEVER_TWO:
                if (locationLeverProperties.getLocationLever() <= lever){
                    validateEntities(updateLogistics);
                    provinceRepository.updateLogisticProvinceLv2(updateLogistics.getId(), updateLogistics.getFfmId(),updateLogistics.getLmId(),updateLogistics.getWhId());
                }else {
                    throw  new IllegalArgumentException(ErrorMessages.FORBIDDEN.getMessage());
                }
                break;
            case  LEVER_THREE:
                    validateEntities(updateLogistics);
                    provinceRepository.updateLogisticProvinceLv3(updateLogistics.getId(), updateLogistics.getFfmId(),updateLogistics.getLmId(),updateLogistics.getWhId());
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
