package com.exampledeliverynew.deliverynew.controller;

import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateLogistics;
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
    public DataResponse<List<DistrictDTO>>  getAllDistrict(@RequestParam Long lever, @RequestParam Long provinceId){
        List<DistrictDTO> districtDTOS = districtService.getAllLeverDistrict(lever, provinceId);
        return  DataResponse.ok(districtDTOS);
    }

    @PutMapping("/update")
    public DataResponse<UpdateLogistics> updateDeliveryProvince(@RequestParam int lever, @RequestBody UpdateLogistics updateLogistics){
        UpdateLogistics updateDelivery1 = districtService.updateDeliveryDistrict(lever, updateLogistics);
        return DataResponse.ok(updateDelivery1);
    }



}
