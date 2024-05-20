package com.exampledeliverynew.deliverynew.dto;


import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import com.exampledeliverynew.deliverynew.repository.DeliveryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateDelivery {

    private Long locationId;
    private Long ffmId;
    private Long lmId;
    private Long whId;

    public UpdateDelivery(DefaultDelivery defaultDelivery){
        this.locationId =defaultDelivery.getLocationId();
        this.ffmId  = defaultDelivery.getFfmId();
        this.lmId = defaultDelivery.getLmId();
        this.whId = defaultDelivery.getWarehouseId();
    }

}
