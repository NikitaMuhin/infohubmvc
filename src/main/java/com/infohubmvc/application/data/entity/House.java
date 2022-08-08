package com.infohubmvc.application.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HOUSES")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String houseAddress;
    private Long numberBlocks;
    private Long numberFloors;
    private Long numberStairs;
    private BigDecimal totalArea;
    private Long yearBuilt;
    private Boolean hasLift;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status")
    private StatusCode status;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condominium_id")
    private Condominium condominium;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "house")
    private List<Property> property;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "house")
    private List<ConsumptionPoint> consumptionPoints;
}
