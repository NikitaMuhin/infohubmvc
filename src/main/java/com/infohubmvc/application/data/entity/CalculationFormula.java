package com.infohubmvc.application.data.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CALCULATION_FORMULA")
public class CalculationFormula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calculation_type")
    private CalculationType calculationType;

    private String formula;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Boolean active;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "calculationFormula")
    //private List<ConsumptionPoint> consumptionPoints;
}