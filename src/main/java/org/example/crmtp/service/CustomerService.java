package org.example.crmtp.service;

import org.example.crmtp.dto.CustomerDTO;
import org.example.crmtp.dto.OrderDTO;
import org.example.crmtp.model.CustomerModel;
import org.example.crmtp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Récupérer tous les customer mais en evitant la répétition à l'infini
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerModel> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        for (CustomerModel customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setCompanyName(customer.getCompanyName());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
            customerDTO.setAddress(customer.getAddress());
            customerDTO.setZipCode(customer.getZipCode());
            customerDTO.setCountry(customer.getCountry());
            customerDTO.setCity(customer.getCity());
            customerDTO.setState(customer.getState());

            List<OrderDTO> orderDTOs = customer.getOrders().stream()
                    .map(order -> new OrderDTO(
                            order.getId(),
                            order.getServiceType(),
                            order.getTva(),
                            order.getNbDays(),
                            order.getTotalExcludeTax(),
                            order.getState(),
                            order.getComment(),
                            null  // pour eviter la répétition de customer
                    ))
                    .collect(Collectors.toList());

            customerDTO.setOrders(orderDTOs);
            customerDTOs.add(customerDTO);
        }

        return customerDTOs;
    }


    // Récupérer un customer par sopn ID
    public Optional<CustomerModel> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Add un customer
    public CustomerModel createCustomer(CustomerModel customer) {
        return customerRepository.save(customer);
    }

    // Update un customer
    public CustomerModel updateCustomer(Long id, CustomerModel updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setCompanyName(updatedCustomer.getCompanyName());
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customer.setAddress(updatedCustomer.getAddress());
            customer.setZipCode(updatedCustomer.getZipCode());
            customer.setCountry(updatedCustomer.getCountry());
            customer.setCity(updatedCustomer.getCity());
            customer.setState(updatedCustomer.getState());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }

    // Suppr un customer via l'ID
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}

