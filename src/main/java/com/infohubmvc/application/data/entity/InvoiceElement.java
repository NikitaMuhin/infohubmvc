package com.infohubmvc.application.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INVOICE_ELEMENTS")
public class InvoiceElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateFrom;
    private LocalDate payUntil;
    private Long indiceStart;
    private Long indiceEnd;
    private BigDecimal untCost;
    private BigDecimal units;
    private BigDecimal calculatedAmount;
    private BigDecimal debt;
    private BigDecimal penalty;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consumption_point_id")
    private ConsumptionPoint consumptionPoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

}