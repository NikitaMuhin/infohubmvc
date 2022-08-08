package com.infohubmvc.application.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "INVOICES")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condominium_id")
    private Condominium condominium;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;

    private Integer invMonth;
    private Integer invYear;
    private BigDecimal calculatedAmount;
    private BigDecimal debt;
    private BigDecimal penalty;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

}