package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.config.LocationLeverProperties;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateLogistics;
import com.exampledeliverynew.deliverynew.dto.impl.SubdistrictDTOImpl;
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
    private  final PartnerRepository partnerRepository;
    private  final WarehouseRepository warehouseRepository;
    private  final LocationLeverProperties locationLeverProperties;


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
    public UpdateLogistics updateDeliverySubdistrict(int lever, UpdateLogistics updateLogistics) {
        Long count = subdistrictRepository.checkExitsSubdistrict(updateLogistics.getId());
        if (count.equals(0l)){
            throw  new IllegalArgumentException(ErrorMessages.INVALID_VALUE.getMessage());
        }

        switch (lever){
            case LEVER_ONE:
                if (locationLeverProperties.getLocationLever() <= lever){
                    validateEntities(updateLogistics);
                    subdistrictRepository.updateDeliverySubdistrictLv1(updateLogistics.getId(), updateLogistics.getFfmId(),updateLogistics.getLmId(),updateLogistics.getWhId());
                }else {
                    throw  new IllegalArgumentException(ErrorMessages.FORBIDDEN.getMessage());
                }
                break;
            case LEVER_TWO:
                if (locationLeverProperties.getLocationLever() <=lever){
                    validateEntities(updateLogistics);
                    subdistrictRepository.updateDeliverySubdistrictLv2(updateLogistics.getId(), updateLogistics.getFfmId(),updateLogistics.getLmId(),updateLogistics.getWhId());
                }else {
                    throw  new IllegalArgumentException(ErrorMessages.FORBIDDEN.getMessage());
                }
            case LEVER_THREE:
                validateEntities(updateLogistics);
                subdistrictRepository.updateDeliverySubdistrictLv3(updateLogistics.getId(), updateLogistics.getFfmId(),updateLogistics.getLmId(),updateLogistics.getWhId());
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
