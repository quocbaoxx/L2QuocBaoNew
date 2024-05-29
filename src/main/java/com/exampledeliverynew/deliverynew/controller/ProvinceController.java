package com.exampledeliverynew.deliverynew.controller;


import com.exampledeliverynew.deliverynew.commons.DataResponse;
import com.exampledeliverynew.deliverynew.dto.ProvinceDTO;
import com.exampledeliverynew.deliverynew.dto.UpdateLogistics;
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
    public DataResponse<UpdateLogistics> updateDeliveryProvince(@RequestParam int lever, @RequestBody UpdateLogistics updateLogistics){
        UpdateLogistics updateDelivery1 = provinceService.updateDeliveryProvince(lever, updateLogistics);
        return DataResponse.ok(updateDelivery1);
    }



}
