package org.example.crmtp.dto;

import lombok.*;
import org.example.crmtp.model.CustomerState;

import java.util.List;

@Getter
@Setter
// Génère un constructeur sans arguments
@NoArgsConstructor
// Génère un constructeur avec tous les arguments
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String country;
    private String city;
    private CustomerState state;

    private List<OrderDTO> orders;
}
