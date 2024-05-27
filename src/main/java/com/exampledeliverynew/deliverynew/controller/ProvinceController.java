package com.exampledeliverynew.deliverynew.controller;


import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateDelivery;
import com.exampledeliverynew.deliverynew.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private  final ProvinceService provinceService;

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



}
