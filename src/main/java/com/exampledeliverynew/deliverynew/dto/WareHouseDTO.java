package com.exampledeliverynew.deliverynew.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class WareHouseDTO  implements  Comparable<WareHouseDTO>{

    private Long id;
    private  String name;

    public WareHouseDTO(LocationResult locationResult) {
        this.id  = locationResult.getWarehouseId();
        this.name = locationResult.getWarehouseName();
    }

    @Override
    public int compareTo(@NotNull WareHouseDTO  wareHouseDTO) {
        if (this.getId() ==null && wareHouseDTO.getId() == null){
            return  0;
        }else if (this.getId() ==null){
            return -1;
        }else  if (wareHouseDTO.getId() == null){
            return  1;
        }else {
            return (int) (this.getId() - wareHouseDTO.getId());
        }
    }
}
