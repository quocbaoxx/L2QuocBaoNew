package com.exampledeliverynew.deliverynew.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticDTO {

    private Set<FulfilmentDTO> fulfilmentDTOS = new TreeSet<>(Comparator.comparing(FulfilmentDTO::getId));
    private Set<LastmileDTO> lastmileDTOS = new TreeSet<>(Comparator.comparing(LastmileDTO::getId));
    private Set<WareHouseDTO> wareHouseDTOS = new TreeSet<>(Comparator.comparing(WareHouseDTO::getId));


}
