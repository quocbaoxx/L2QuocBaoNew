package com.exampledeliverynew.deliverynew.dto.impl;

import com.exampledeliverynew.deliverynew.dto.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.TreeSet;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDtoImpl implements LocationDTO {

    private  final Long id;
    private  final String name;
    private  final LogisticDTO logisticDTO;

    public LocationDtoImpl(LocationResult locationResult) {
        this.id = locationResult.getLocationId();
        this.name = buildName(locationResult);
        this.logisticDTO = buildLogisticDTO(locationResult);
    }

    private String buildName(LocationResult locationResult) {
        String provinceName = locationResult.getprovinceName() != null ? locationResult.getprovinceName() : "";
        String districtName = locationResult.getdistrictName() != null ? locationResult.getdistrictName() : "";
        String subdistrictName = locationResult.getsubdistrictName() != null ? locationResult.getsubdistrictName() : "";
        return provinceName + ", " + districtName + ", " + subdistrictName;
    }

    private LogisticDTO buildLogisticDTO(LocationResult locationResult) {
        Set<FulfilmentDTO> ffmSet = new TreeSet<>();
        Set<LastmileDTO> lmSet = new TreeSet<>();
        Set<WareHouseDTO> whSet = new TreeSet<>();

        Long ffmId = locationResult.getFfmId();
        String ffmName = locationResult.getFfmName();
        Long ffmType = locationResult.getFfmType();
        ffmSet.add(new FulfilmentDTO(ffmId, ffmName, ffmType));

        Long lmId = locationResult.getLmId();
        String lmName = locationResult.getLmName();
        Long lmType = locationResult.getLmType();
        lmSet.add(new LastmileDTO(lmId, lmName, lmType));

        Long whId = locationResult.getWarehouseId();
        String whName = locationResult.getWarehouseName();
        whSet.add(new WareHouseDTO(whId, whName));

        return new LogisticDTO(ffmSet, lmSet, whSet);
    }


    @Override
    public void addFulfilmentDTO(LocationResult locationResult) {
        FulfilmentDTO fulfilmentDTO = new FulfilmentDTO(locationResult);
        if (fulfilmentDTO.getId() != null){
            this.getLogisticDTO().getFulfilmentDTOS().add(fulfilmentDTO);
        }
    }

    @Override
    public void addLastmileDTO(LocationResult locationResult) {
        LastmileDTO lastmileDTO = new LastmileDTO(locationResult);
        if (lastmileDTO.getId() != null){
            this.getLogisticDTO().getLastmileDTOS().add(lastmileDTO);
        }
    }

    @Override
    public void addWarehouse(LocationResult locationResult) {
        WareHouseDTO wareHouseDTO = new WareHouseDTO(locationResult);
        if (wareHouseDTO.getId() != null){
            this.getLogisticDTO().getWareHouseDTOS().add(wareHouseDTO);
        }
    }
}
