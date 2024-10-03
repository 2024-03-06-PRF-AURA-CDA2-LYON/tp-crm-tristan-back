package org.example.crmtp.dto;

import lombok.*;
import org.example.crmtp.model.OrderState;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String serviceType;
    private BigDecimal tva;
    private Integer nbDays;
    private BigDecimal totalExcludeTax;
    private OrderState state;
    private String comment;
    private CustomerDTO customer;
}
