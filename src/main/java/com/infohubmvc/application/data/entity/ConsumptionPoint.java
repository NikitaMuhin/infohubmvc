package com.infohubmvc.application.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONSUMPTION_POINTS")
public class ConsumptionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long parentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private ConsumptionPoint parent;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_code")
    private ServiceType serviceType;

    private Long discountValue;
    private String counterNumber;
    private LocalDate validityDateCounter;
    private Long initialCounterIndex;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condominium_id")
    private Condominium condominium;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private Property property;

    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "calculation_code", referencedColumnName = "discount_code")
    //private CalculationFormula calculationFormula;


    private String discountCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calculation_code")
    private CalculationType calculationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

}