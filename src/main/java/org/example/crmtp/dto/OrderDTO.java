package org.example.crmtp.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.crmtp.model.OrderState;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String serviceType;
    private BigDecimal tva;
    private Integer nbDays;
    private BigDecimal totalExcludeTax;
    private OrderState state;
    private String comment;
    @Setter
    private CustomerDTO customer;


    public OrderDTO() {
    }


}
