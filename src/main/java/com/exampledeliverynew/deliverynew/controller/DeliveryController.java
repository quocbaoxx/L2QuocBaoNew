package com.exampledeliverynew.deliverynew.controller;

import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.export.ExcelExporter;
import com.exampledeliverynew.deliverynew.service.DeliveryService;
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
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private  final DeliveryService deliveryService;
    private  final ExcelExporter excelExporter;

    @GetMapping("/logistics")
    public DataResponse<List<LocationResult>> getAllLogistics(){
        List<LocationResult> locationResults = deliveryService.getAllLogistics();
        return DataResponse.ok(locationResults);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel(@RequestParam int lever) {
        try {
            List<LocationResult> locationResults = deliveryService.getAllExpost(lever);
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
