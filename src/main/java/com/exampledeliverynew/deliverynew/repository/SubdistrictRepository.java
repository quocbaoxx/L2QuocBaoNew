package com.exampledeliverynew.deliverynew.repository;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.entity.Subdistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubdistrictRepository extends JpaRepository<Subdistrict, Long> {

    @Query(value = "SELECT COUNT(*) > 0 FROM lc_subdistrict WHERE subdistrict_id = :id", nativeQuery = true)
    boolean existsById(@Param("id") Long id);

    @Query(value = "SELECT " +
            "sub.subdistrict_id AS locationId, " +
            "prov.province_name AS provinceName, " +
            "dt.district_name AS districtName, " +
            "sub.subdistrict_name AS subdistrictName, " +
            "ffm.partner_id AS ffmId, " +
            "ffm.partner_name AS ffmName, " +
            "ffm.pn_type AS ffmType, " +
            "lm.partner_id AS lmId, " +
            "lm.partner_name AS lmName, " +
            "lm.pn_type AS lmType, " +
            "wh.warehouse_id AS warehouseId, " +
            "wh.warehouse_name AS warehouseName " +
            "FROM " +
            "lc_subdistrict sub  " +
            "LEFT JOIN " +
            "lc_district dt ON dt.district_id = sub.district_id " +
            "LEFT JOIN " +
            "lc_province prov ON dt.province_id = prov.province_id " +
            "LEFT JOIN " +
            "cf_default_delivery cf ON prov.province_id = cf.location_id " +
            "LEFT JOIN " +
            "bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "LEFT JOIN " +
            "bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "LEFT JOIN " +
            "bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id " +
            "WHERE  dt.district_id = :districtId ", nativeQuery = true)
    List<LocationResult> getSubDistrictAndLogisticLevelOne(@Param("districtId") Long districtId);

    @Query(value = "SELECT " +
            "sub.subdistrict_id AS locationId, " +
            "ds.district_name AS districtName, " +
            "prov.province_name AS provinceName, " +
            "sub.subdistrict_name AS subdistrictName, " +
            "ffm.partner_id AS ffmId, " +
            "ffm.partner_name AS ffmName, " +
            "ffm.pn_type AS ffmType, " +
            "lm.partner_id AS lmId, " +
            "lm.partner_name AS lmName, " +
            "lm.pn_type AS lmType, " +
            "wh.warehouse_id AS warehouseId, " +
            "wh.warehouse_name AS warehouseName " +
            "FROM " +
            "lc_subdistrict sub " +
            "LEFT JOIN " +
            "lc_district ds ON ds.district_id = sub.district_id " +
            "LEFT JOIN " +
            "lc_province prov ON ds.province_id = prov.province_id " +
            "LEFT JOIN " +
            "cf_default_delivery cf ON ds.district_id = cf.location_id " +
            "LEFT JOIN " +
            "bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "LEFT JOIN " +
            "bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "LEFT JOIN " +
            "bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id " +
            "WHERE ds.district_id = :districtId ", nativeQuery = true)
    List<LocationResult> getSubDistrictAndLogisticLevelTwo(@Param("districtId") Long districtId);

    @Query(value =
            "SELECT " +
                    "    sub.subdistrict_id AS locationId, " +
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
                    "    lc_subdistrict sub " +
                    "LEFT JOIN " +
                    "    lc_district ds ON sub.district_id = ds.district_id " +
                    "LEFT JOIN " +
                    "     lc_province prov  ON ds.province_id = prov.province_id " +
                    "LEFT JOIN " +
                    "    cf_default_delivery cf ON sub.subdistrict_id = cf.location_id " +
                    "LEFT JOIN " +
                    "    bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
                    "LEFT JOIN " +
                    "    bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
                    "LEFT JOIN " +
                    "    bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id " +
                    "WHERE ds.district_id = :districtId", nativeQuery = true)
    List<LocationResult> getSubDistrictAndLogisticLevelThree(@Param("districtId") Long districtId);


    @Modifying
    @Query(value = "UPDATE cf_default_delivery SET ffm_partner_id = :ffmId, lm_partner_id = :lmId, warehouse_id = :whId WHERE location_id = :locationId", nativeQuery = true)
    void updateDeliveryDistrict(Long locationId, Long ffmId, Long lmId, Long whId);
}
