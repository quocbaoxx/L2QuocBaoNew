package com.exampledeliverynew.deliverynew.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "locationId",
        "provinceName",
        "districtName",
        "subdistrictName",
        "warehouseId",
        "warehouseName",
        "lmId",
        "lmName",
        "lmType",
        "ffmId",
        "ffmName",
        "ffmType"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface LocationResult {
    Long  getLocationId();
    String  getProvinceName();
    String  getDistrictName();
    String getSubdistrictName();
    Long getWarehouseId();
    String getWarehouseName();
    Long getLmId();
    String getLmName();
    Long getLmType();
    Long getFfmId();
    String getFfmName();
    Long getFfmType();
}
