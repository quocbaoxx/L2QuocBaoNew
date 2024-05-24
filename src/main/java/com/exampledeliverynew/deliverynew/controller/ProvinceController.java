package com.exampledeliverynew.deliverynew.controller;


import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.export.ExcelExporter;
import com.exampledeliverynew.deliverynew.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private  final ProvinceService provinceService;
    private  final  ExcelExporter excelExporter;

    @GetMapping("/all")
    public DataResponse<List<ProvinceDTO>> getAllLogisticsProvince(@RequestParam Long leverMapping){
        List<ProvinceDTO> provinceDTOS = provinceService.getAllLever(leverMapping);
        return DataResponse.ok(provinceDTOS);
    }


    @PutMapping("/update")
    public DataResponse<UpdateDelivery> updateDeliveryProvince(@RequestParam int lever, @RequestBody UpdateDelivery updateDelivery){
        UpdateDelivery updateDelivery1 = provinceService.updateDeliveryProvince(lever, updateDelivery);
        return DataResponse.ok(updateDelivery1);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel(@RequestParam int lever) {
        try {
            List<LocationResult> locationResults = provinceService.getAllExpost(lever);
            ByteArrayInputStream in = excelExporter.locationResultsToExcel(locationResults);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=location_results.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(in.readAllBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
