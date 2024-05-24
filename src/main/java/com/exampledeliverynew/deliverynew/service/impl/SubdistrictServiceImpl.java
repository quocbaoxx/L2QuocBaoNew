package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.dto.impl.SubdistrictDTOImpl;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import com.exampledeliverynew.deliverynew.repository.DistrictRepository;
import com.exampledeliverynew.deliverynew.repository.ProvinceRepository;
import com.exampledeliverynew.deliverynew.repository.SubdistrictRepository;
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

    @Override
    public List<SubdistrictDTO> getAllLeverSubdistrict(Long lever) {

        List<LocationResult> locationResults ;
        switch (lever.intValue()){
            case  LEVER_PROVINCE:
                locationResults = subdistrictRepository.getSubDistrictAndLogisticLevelOne();
                break;
            case LEVER_DISTRICT:
                locationResults = subdistrictRepository.getSubDistrictAndLogisticLevelTwo();
                break;
            case  LEVER_SUBDISTRICT:
                locationResults = subdistrictRepository.getSubDistrictAndLogisticLevelThree();
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
            subdistrictRepository.updateDeliveryDistrict(updateDelivery.getLocationId(), updateDelivery.getFfmId(), updateDelivery.getLmId(), updateDelivery.getWhId());
        }else {
            throw  new IndexOutOfBoundsException(ErrorMessages.FORBIDDEN.getMessage());
        }
        defaultDelivery = new DefaultDelivery(updateDelivery.getLocationId(),updateDelivery.getFfmId(),updateDelivery.getLmId(),updateDelivery.getWhId());

        return new UpdateDelivery(defaultDelivery);
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
