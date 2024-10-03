package org.example.crmtp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(name = "tva", nullable = false)
    private BigDecimal tva;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference // Empêche la sérialisation récursive
    private CustomerModel customer;

    @Column(name = "nb_days", nullable = false)
    private Integer nbDays;

    @Column(name = "total_exclude_tax", nullable = false)
    private BigDecimal totalExcludeTax;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state")
    private OrderState state;

    @Column(name = "comment")
    private String comment;

}

