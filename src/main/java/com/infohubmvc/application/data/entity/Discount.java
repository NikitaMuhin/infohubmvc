package com.infohubmvc.application.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DISCOUNTS")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discount_code")
    private DiscountType discountType;

    private String description;
    private BigDecimal discountValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "discount_unit")
    private Unit discountUnit;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

    private Boolean active;

}