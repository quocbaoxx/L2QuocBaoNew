package com.exampledeliverynew.deliverynew.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class LastmileDTO implements  Comparable<LastmileDTO> {

    private  Long id;
    private  String name;
    private  Long type;

    public LastmileDTO(LocationResult locationResult) {
        this.id = locationResult.getLmId();
        this.name = locationResult.getLmName();
        this.type = locationResult.getLmType();
    }

    @Override
    public int compareTo(@NotNull  LastmileDTO  lastmileDTO) {
        if (this.getId() == null  && lastmileDTO.getId() == null){
            return  0 ;
        }else  if (this.getId() == null){
            return  -1;
        }else  if (lastmileDTO.getId() == null){
            return  1;
        }else {
            return (int) (this.getId() - lastmileDTO.getId());
        }
    }
}
