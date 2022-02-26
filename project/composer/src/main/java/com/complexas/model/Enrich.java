package com.complexas.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "enrichment")
public class Enrich {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    private String type;
    private int maxWeight;
    private String typeOfDelivery;
    private int volume;
}
