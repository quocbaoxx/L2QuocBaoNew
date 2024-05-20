package com.exampledeliverynew.deliverynew.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class FulfilmentDTO  implements  Comparable<FulfilmentDTO>{

    private Long id;
    private String  name;
    private Long type;

    public FulfilmentDTO(LocationResult locationResult){
        this.id = locationResult.getFfmId();
        this.name = locationResult.getFfmName();
        this.type = locationResult.getFfmType();
    }
    @Override
    public int compareTo(@NotNull FulfilmentDTO fulfilmentDTO) {
        if (this.getId() == null && fulfilmentDTO.getId() ==null){
            return 0;
        }else if (this.getId() == null){
            return  -1;
        }else if (fulfilmentDTO.getId() == null){
            return  1;
        }else {
            return (int) (this.getId() - fulfilmentDTO.getId());
        }

    }
}
