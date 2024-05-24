package com.exampledeliverynew.deliverynew.controller;

import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {

    private  final DistrictService districtService;

    @GetMapping("/all")
    public DataResponse<List<DistrictDTO>>  getAllDistrict(@RequestParam Long lever){
        List<DistrictDTO> districtDTOS = districtService.getAllLeverDistrict(lever);
        return  DataResponse.ok(districtDTOS);
    }

    @PutMapping("/update")
    public DataResponse<UpdateDelivery> updateDeliveryProvince(@RequestParam int lever, @RequestBody UpdateDelivery updateDelivery){
        UpdateDelivery updateDelivery1 = districtService.updateDeliveryDistrict(lever, updateDelivery);
        return DataResponse.ok(updateDelivery1);
    }



}
