package com.exampledeliverynew.deliverynew.repository;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {


    @Query(value = "SELECT " +
            "dt.district_id AS locationId, " +
            "prov.province_name AS provinceName, " +
            "dt.district_name AS districtName, " +
            "ffm.partner_id AS ffmId, " +
            "ffm.partner_name AS ffmName, " +
            "ffm.pn_type AS ffmType, " +
            "lm.partner_id AS lmId, " +
            "lm.partner_name AS lmName, " +
            "lm.pn_type AS lmType, " +
            "wh.warehouse_id AS warehouseId, " +
            "wh.warehouse_name AS warehouseName " +
            "FROM " +
            "lc_district dt " +
            "LEFT JOIN " +
            "lc_province prov  ON dt.province_id = prov.province_id " +
            "LEFT JOIN " +
            "cf_default_delivery cf ON prov.province_id = cf.location_id " +
            "LEFT JOIN " +
            "bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "LEFT JOIN " +
            "bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "LEFT JOIN " +
            "bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id " +
            "WHERE  prov.province_id = :provinceId", nativeQuery = true)
    List<LocationResult> getDistrictAndLogisticLevelOne(@Param("provinceId") Long provinceId);


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
            "FROM lc_district ds " +
            "LEFT JOIN  lc_province prov  ON prov.province_id = ds.province_id " +
            "LEFT JOIN cf_default_delivery cf ON ds.district_id = cf.location_id " +
            "LEFT JOIN bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "LEFT JOIN bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "LEFT JOIN bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id " +
            "WHERE prov.province_id = :provinceId", nativeQuery = true)
    List<LocationResult> getDistrictAndLogisticLevelTwo(@Param("provinceId") Long provinceId);


    @Query(value =
            "SELECT " +
                    "    ds.district_id AS locationId, " +
                    "    sub.subdistrict_name AS name, " +
                    "    ds.district_name as districtName," +
                    "    prov.province_name AS provinceName,"+
                    "    ffm.partner_id AS ffmId, " +
                    "    ffm.partner_name AS ffmName, " +
                    "    ffm.pn_type AS ffmType, " +
                    "    lm.partner_id AS lmId, " +
                    "    lm.partner_name AS lmName, " +
                    "    lm.pn_type AS lmType, " +
                    "    wh.warehouse_id AS warehouseId, " +
                    "    wh.warehouse_name AS warehouseName " +
                    "FROM " +
                    "    lc_district ds " +
                    "LEFT JOIN " +
                    "     lc_province prov ON prov.province_id = ds.province_id " +
                    "LEFT JOIN " +
                    "    lc_subdistrict sub ON sub.district_id = ds.district_id " +
                    "LEFT JOIN " +
                    "    cf_default_delivery cf ON sub.subdistrict_id = cf.location_id " +
                    "LEFT JOIN " +
                    "    bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
                    "LEFT JOIN " +
                    "    bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
                    "LEFT JOIN " +
                    "    bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id " +
                    "WHERE prov.province_id = :provinceId", nativeQuery = true)
    List<LocationResult> getDistrictAndLogisticLevelThree(@Param("provinceId") Long provinceId);

    @Query(value = "SELECT count(d.district_id) from lc_district d WHERE d.district_id = :districtId",nativeQuery = true)
    Long checkExitsDistrict(@Param("districtId") Long districtId);

    @Modifying
    @Query(value = "UPDATE cf_default_delivery " +
            "SET ffm_partner_id = :ffmId, " +
            "    lm_partner_id = :lmId, " +
            "    warehouse_id = :whId " +
            "WHERE location_id IN (  " +
            "SELECT pro.province_id " +
            "FROM lc_province pro join lc_district d  " +
            "    ON pro.province_id = d.province_id " +
            "WHERE  d.district_id = :districtId )", nativeQuery = true)
    void updateDeliveryDistrictLv1(@Param("districtId") Long districtId, @Param("ffmId") Long ffmId, @Param("lmId") Long lmId, @Param("whId") Long whId);

    @Modifying
    @Query(value = "UPDATE cf_default_delivery " +
            "SET ffm_partner_id = :ffmId, " +
            "    lm_partner_id = :lmId, " +
            "    warehouse_id = :whId " +
            "WHERE location_id IN (  " +
            "SELECT d.district_id " +
            "FROM lc_district d  " +
            "WHERE  d.district_id = :districtId )", nativeQuery = true)
    void updateDeliveryDistrictLv2(@Param("districtId") Long districtId, @Param("ffmId") Long ffmId, @Param("lmId") Long lmId, @Param("whId") Long whId);

    @Modifying
    @Query(value = "UPDATE cf_default_delivery " +
            "SET ffm_partner_id = :ffmId, " +
            "    lm_partner_id = :lmId, " +
            "    warehouse_id = :whId " +
            "WHERE location_id IN (  " +
            "SELECT sub.subdistrict_id " +
            "FROM lc_subdistrict sub join lc_district d  " +
            "    ON sub.district_id = d.district_id " +
            "WHERE  d.district_id = :districtId )", nativeQuery = true)
    void updateDeliveryDistrictLv3(@Param("districtId") Long districtId, @Param("ffmId") Long ffmId, @Param("lmId") Long lmId, @Param("whId") Long whId);


}
