package com.exampledeliverynew.deliverynew.repository;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<DefaultDelivery, Long> {


    @Query(value = "SELECT " +
            "cf.location_id AS locationId, " +
            "ffm.partner_id AS ffmId, " +
            "ffm.partner_name AS ffmName, " +
            "ffm.pn_type AS ffmType, " +
            "lm.partner_id AS lmId, " +
            "lm.partner_name AS lmName, " +
            "lm.pn_type AS lmType, " +
            "wh.warehouse_id AS warehouseId, " +
            "wh.warehouse_name AS warehouseName " +
            "FROM cf_default_delivery cf " +
            "JOIN bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "JOIN bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "JOIN bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id", nativeQuery = true)
    List<LocationResult> getAllLogistics();


    @Query(value = "SELECT " +
            "prov.province_id AS locationId, " +
            "prov.province_name AS provinceName, " +
            "ffm.partner_id AS ffmId, " +
            "ffm.partner_name AS ffmName, " +
            "ffm.pn_type AS ffmType, " +
            "lm.partner_id AS lmId, " +
            "lm.partner_name AS lmName, " +
            "lm.pn_type AS lmType, " +
            "wh.warehouse_id AS warehouseId, " +
            "wh.warehouse_name AS warehouseName " +
            "FROM lc_province prov " +
            "JOIN cf_default_delivery cf ON prov.province_id = cf.location_id " +
            "JOIN bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "JOIN bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "JOIN bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id;", nativeQuery = true)
    List<LocationResult> getProvinceInformation();

    @Query(value = "SELECT " +
            "ds.district_id AS locationId, " +
            "ds.district_name AS districtName, " +
            "prov.province_name AS provinceName,"+
            "ffm.partner_id AS ffmId, " +
            "ffm.partner_name AS ffmName, " +
            "ffm.pn_type AS ffmType, " +
            "lm.partner_id AS lmId, " +
            "lm.partner_name AS lmName, " +
            "lm.pn_type AS lmType, " +
            "wh.warehouse_id AS warehouseId, " +
            "wh.warehouse_name AS warehouseName " +
            "FROM lc_province prov " +
            "JOIN lc_district ds ON prov.province_id = ds.province_id " +
            "JOIN cf_default_delivery cf ON ds.district_id = cf.location_id " +
            "JOIN bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "JOIN bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "JOIN bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id ", nativeQuery = true)
    List<LocationResult> getDistrictInformation();

    @Query(value =
            "SELECT " +
                    "    cf.location_id AS locationId, " +
                    "    sub.subdistrict_name AS name, " +
                    "    ds.district_name as districtName," +
                    "    prov.province_name AS provinceName,"+
                    "    sub.subdistrict_name AS subdistrictName,"+
                    "    ffm.partner_id AS ffmId, " +
                    "    ffm.partner_name AS ffmName, " +
                    "    ffm.pn_type AS ffmType, " +
                    "    lm.partner_id AS lmId, " +
                    "    lm.partner_name AS lmName, " +
                    "    lm.pn_type AS lmType, " +
                    "    wh.warehouse_id AS warehouseId, " +
                    "    wh.warehouse_name AS warehouseName " +
                    "FROM " +
                    "    lc_province prov " +
                    "JOIN " +
                    "    lc_district ds ON prov.province_id = ds.province_id " +
                    "JOIN " +
                    "    lc_subdistrict sub ON sub.district_id = ds.district_id " +
                    "JOIN " +
                    "    cf_default_delivery cf ON sub.subdistrict_id = cf.location_id " +
                    "JOIN " +
                    "    bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
                    "JOIN " +
                    "    bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
                    "JOIN " +
                    "    bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id " , nativeQuery = true)
    List<LocationResult> getSubdistrictInformation();
}
