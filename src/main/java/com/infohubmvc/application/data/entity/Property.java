package com.infohubmvc.application.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROPERTIES")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_type")
    private PropertyType propertyType;

    private String firstName;
    private String lastName;
    private Long floor;
    private String address;
    private String phoneNumbers;
    private String email;
    private Integer staircase;
    private BigDecimal area;
    private Integer numberRooms;
    private Integer numberMembers;
    private Long userGroupId;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teller_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
    private List<ConsumptionPoint> consumptionPoints;
}