package com.exampledeliverynew.deliverynew.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id"})
public interface LocationDTO {

    Long  getId();
    LogisticDTO getLogisticDTO();

    void addFulfilmentDTO(LocationResult locationResult);

    void  addLastmileDTO(LocationResult locationResult);

    void  addWarehouse(LocationResult locationResult);



}
