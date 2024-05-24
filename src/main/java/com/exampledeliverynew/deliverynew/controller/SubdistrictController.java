package com.exampledeliverynew.deliverynew.controller;

import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.service.SubdistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subdistricts")
@RequiredArgsConstructor
public class SubdistrictController {

    private  final SubdistrictService subdistrictService;

    @RequestMapping("/all")
    public DataResponse<List<SubdistrictDTO>> getAllSubdistrict(@RequestParam Long lever){
        List<SubdistrictDTO> subdistrictDTOS  = subdistrictService.getAllLeverSubdistrict(lever);
        return DataResponse.ok(subdistrictDTOS);
    }

    @PutMapping("/update")
    public DataResponse<UpdateDelivery> updateDeliveryProvince(@RequestParam int lever, @RequestBody UpdateDelivery updateDelivery){
        UpdateDelivery updateDelivery1 = subdistrictService.updateDeliverySubdistrict(lever, updateDelivery);
        return DataResponse.ok(updateDelivery1);
    }

}
