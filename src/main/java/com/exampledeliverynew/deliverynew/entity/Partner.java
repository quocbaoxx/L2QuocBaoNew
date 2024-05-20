package com.exampledeliverynew.deliverynew.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bp_partner")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long id;

    @Column(name = "partner_name")
    private String name;

    @Column(name = "pn_type")
    private Long type;

}
