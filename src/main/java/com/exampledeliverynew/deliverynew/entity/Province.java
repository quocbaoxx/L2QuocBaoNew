package com.exampledeliverynew.deliverynew.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lc_province")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id")
    private Long id;


    @Column(name = "province_name")
    private String name;

}