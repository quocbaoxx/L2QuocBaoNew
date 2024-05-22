package com.exampledeliverynew.deliverynew.repository;

import com.exampledeliverynew.deliverynew.dto.LocationResult;
import com.exampledeliverynew.deliverynew.entity.DefaultDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Query(value = "UPDATE cf_default_delivery SET ffm_partner_id = :ffmId, lm_partner_id = :lmId, warehouse_id = :whId WHERE location_id = :locationId", nativeQuery = true)
    void updateDelivery(@Param("locationId") Long locationId, @Param("ffmId") Long ffmId, @Param("lmId") Long lmId, @Param("whId") Long whId);

}
