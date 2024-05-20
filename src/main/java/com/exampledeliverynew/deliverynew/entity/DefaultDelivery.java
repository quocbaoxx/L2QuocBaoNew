package com.exampledeliverynew.deliverynew.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cf_default_delivery")
public class DefaultDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "lm_partner_id")
    private Long lmId;

    @Column(name = "ffm_partner_id")
    private Long ffmId;

    @Column(name = "region")
    private String region;


}