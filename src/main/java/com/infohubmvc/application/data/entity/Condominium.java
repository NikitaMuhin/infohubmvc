package com.infohubmvc.application.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONDOMINIUMS")
public class Condominium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String registrationNumber;
    private String officeAddress;
    private String infohubContractNr;

    @Enumerated(EnumType.STRING)
    private CondominiumStatus status;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "condominium")
    private List<House> houses;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "condominium")
    private List<ConsumptionPoint> consumptionPoints;
}