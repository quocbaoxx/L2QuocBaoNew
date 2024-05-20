package com.exampledeliverynew.deliverynew.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lc_district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long id;


    @Column(name = "district_name")
    private String name;

    @Column(name = "province_id")
    private int provinceId;

}
