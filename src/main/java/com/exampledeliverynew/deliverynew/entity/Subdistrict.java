package com.exampledeliverynew.deliverynew.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "lc_subdistrict")
public class Subdistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subdistrict_id")
    private Long id;

    @Column(name = "subdistrict_name")
    private String name;

    @Column(name = "district_id")
    private Long districtId;

}
