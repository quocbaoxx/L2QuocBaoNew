package com.exampledeliverynew.deliverynew.repository;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

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
            "LEFT JOIN cf_default_delivery cf ON prov.province_id = cf.location_id " +
            "LEFT JOIN bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "LEFT JOIN bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "LEFT JOIN bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id", nativeQuery = true)
    List<LocationResult> getProvinceAndLogisticLevelOne();

    @Query(value = "SELECT " +
            "ds.district_id AS locationId, " +
            "ds.district_name AS districtName, " +
            "prov.province_name AS provinceName," +
            "ffm.partner_id AS ffmId, " +
            "ffm.partner_name AS ffmName, " +
            "ffm.pn_type AS ffmType, " +
            "lm.partner_id AS lmId, " +
            "lm.partner_name AS lmName, " +
            "lm.pn_type AS lmType, " +
            "wh.warehouse_id AS warehouseId, " +
            "wh.warehouse_name AS warehouseName " +
            "FROM lc_province prov " +
            "LEFT JOIN lc_district ds ON prov.province_id = ds.province_id " +
            "LEFT JOIN cf_default_delivery cf ON ds.district_id = cf.location_id " +
            "LEFT JOIN bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
            "LEFT JOIN bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
            "LEFT JOIN bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id", nativeQuery = true)
    List<LocationResult> getProvinceAndLogisticLevelTwo();

    @Query(value =
            "SELECT " +
                    "    cf.location_id AS locationId, " +
                    "    ds.district_name as districtName," +
                    "    prov.province_name AS provinceName," +
                    "    sub.subdistrict_name AS subdistrictName," +
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
                    "LEFT JOIN " +
                    "    lc_district ds ON prov.province_id = ds.province_id " +
                    "LEFT JOIN " +
                    "    lc_subdistrict sub ON sub.district_id = ds.district_id " +
                    "LEFT JOIN " +
                    "    cf_default_delivery cf ON sub.subdistrict_id = cf.location_id " +
                    "LEFT JOIN " +
                    "    bp_partner ffm ON cf.ffm_partner_id = ffm.partner_id " +
                    "LEFT JOIN " +
                    "    bp_partner lm ON cf.lm_partner_id = lm.partner_id " +
                    "LEFT JOIN " +
                    "    bp_warehouse wh ON cf.warehouse_id = wh.warehouse_id", nativeQuery = true)
    List<LocationResult> getProvinceAndLogisticLevelThree();

    @Query(value = "SELECT count(p.province_id) from lc_province p WHERE p.province_id = :provinceId", nativeQuery = true)
    Long checkExitsProvince(@Param("provinceId") Long id);

    @Modifying
    @Query(value = "UPDATE cf_default_delivery " +
            "SET ffm_partner_id = :ffmId, " +
            "    lm_partner_id = :lmId, " +
            "    warehouse_id = :whId " +
            "WHERE location_id IN ( " +
            "SELECT province_id " +
            "FROM lc_province " +
            "WHERE province_id = :provinceId )", nativeQuery = true)
    void updateLogisticProvinceLv1(@Param("provinceId") Long provinceId, @Param("ffmId") Long ffmId, @Param("lmId") Long lmId, @Param("whId") Long whId);


    @Modifying
    @Query(value = "UPDATE cf_default_delivery " +
            "SET ffm_partner_id = :ffmId, " +
            "    lm_partner_id = :lmId, " +
            "    warehouse_id = :whId " +
            "Where location_id IN ( " +
            "SELECT d.district_id " +
            "FROM lc_province pro join lc_district d  " +
            "ON pro.province_id = d.province_id " +
            "WHERE  pro.province_id = :provinceId )", nativeQuery = true)
    void updateLogisticProvinceLv2(@Param("provinceId") Long provinceId, @Param("ffmId") Long ffmId, @Param("lmId") Long lmId, @Param("whId") Long whId);

    @Modifying
    @Query(value = "UPDATE cf_default_delivery " +
            "SET ffm_partner_id = :ffmId, " +
            "    lm_partner_id = :lmId, " +
            "    warehouse_id = :whId " +
            "Where location_id IN ( " +
            "SELECT sub.subdistrict_id " +
            "FROM lc_province pro JOIN lc_district d  " +
            "ON pro.province_id = d.province_id " +
            "JOIN lc_subdistrict sub  " +
            "ON sub.district_id = d.district_id " +
            "WHERE  pro.province_id = :provinceId )", nativeQuery = true)
    void updateLogisticProvinceLv3(@Param("provinceId") Long provinceId, @Param("ffmId") Long ffmId, @Param("lmId") Long lmId, @Param("whId") Long whId);



}
