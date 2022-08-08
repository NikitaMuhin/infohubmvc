package com.infohubmvc.application.data.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long groupId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Condominium> condominiums;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<House> houses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Property> property;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<CalculationFormula> calculationFormulas;

/*
    @Enumerated(EnumType.STRING)
    private UserAccessRole accessRole;
*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ConsumptionPoint> consumptionPoints;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Service> services;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();*/

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserRole> userRoles;
}