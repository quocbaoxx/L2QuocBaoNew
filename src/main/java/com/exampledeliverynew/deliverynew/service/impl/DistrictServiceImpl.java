package com.exampledeliverynew.deliverynew.service.impl;

import com.exampledeliverynew.deliverynew.commons.exception.ErrorMessages;
import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.dto.impl.DistrictDTOImpl;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import com.exampledeliverynew.deliverynew.repository.DistrictRepository;
import com.exampledeliverynew.deliverynew.repository.ProvinceRepository;
import com.exampledeliverynew.deliverynew.repository.SubdistrictRepository;
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

    @Override
    public List<DistrictDTO> getAllLeverDistrict(Long lever) {
        List<LocationResult> locationResults ;
        switch (lever.intValue()){
            case  LEVER_PROVINCE:
                locationResults = districtRepository.getProvinceAndLogisticLevelOne();
                break;
            case  LEVER_DISTRICT:
                locationResults = districtRepository.getProvinceAndLogisticLevelTwo();
                break;
            case  LEVER_SUBDISTRICT:
                locationResults = districtRepository.getProvinceAndLogisticLevelThree();
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
            districtRepository.updateDeliveryDistrict(updateDelivery.getLocationId(), updateDelivery.getFfmId(), updateDelivery.getLmId(), updateDelivery.getWhId());
        }else {
            throw  new IndexOutOfBoundsException(ErrorMessages.FORBIDDEN.getMessage());
        }
        defaultDelivery = new DefaultDelivery(updateDelivery.getLocationId(),updateDelivery.getFfmId(),updateDelivery.getLmId(),updateDelivery.getWhId());

        return new UpdateDelivery(defaultDelivery);
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
