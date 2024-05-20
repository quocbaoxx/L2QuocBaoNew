package com.exampledeliverynew.deliverynew.controller;

import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.DistrictDTO;
import com.exampledeliverynew.deliverynew.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



}
