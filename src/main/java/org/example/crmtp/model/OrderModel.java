package org.example.crmtp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order")
@Data
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
    private CustomerModel customer;

    @Column(name = "nb_days", nullable = false)
    private Integer nbDays;

    @Column(name = "total_exclude_tax", nullable = false)
    private BigDecimal totalExcludeTax;

    @Column(name = "state")
    private Integer state;

    @Column(name = "comment")
    private String comment;

}

