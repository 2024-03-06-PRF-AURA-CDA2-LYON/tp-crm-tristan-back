package org.example.crmtp.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.crmtp.model.CustomerState;

import java.util.List;

@Getter
@Setter
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

    // Liste des commandes liées au client
    private List<OrderDTO> orders;

    public CustomerDTO() {
    }
}
