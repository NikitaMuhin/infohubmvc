package com.infohubmvc.application.data.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BENEFICIARIES")
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condominium_id")
    private Condominium condominium;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private Service service;

    @NotEmpty
    private String serviceName;

    @NotEmpty
    private String calculationCode;

    @NotNull
    private BigDecimal tarif;

    @Nullable
    private Long initialCounterIndex;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

}