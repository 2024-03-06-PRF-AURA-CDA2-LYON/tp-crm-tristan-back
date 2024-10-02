package org.example.crmtp.service;

import org.example.crmtp.model.CustomerModel;
import org.example.crmtp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Récupérer tous les customer
    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
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

