package com.exampledeliverynew.deliverynew.controller;

import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.SubdistrictDTO;
import com.exampledeliverynew.deliverynew.service.SubdistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
