package org.example.crmtp.service;

import org.example.crmtp.dto.CustomerDTO;
import org.example.crmtp.dto.OrderDTO;
import org.example.crmtp.model.CustomerModel;
import org.example.crmtp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return customers.stream().map(customer -> {
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

            // Récupérer les commandes sans customer
            List<OrderDTO> orderWithDTO = customer.getOrder().stream()
                    .map(order -> {
                        OrderDTO orderDTO = new OrderDTO();
                        orderDTO.setId(order.getId());
                        orderDTO.setServiceType(order.getServiceType());
                        orderDTO.setTva(order.getTva());
                        orderDTO.setNbDays(order.getNbDays());
                        orderDTO.setTotalExcludeTax(order.getTotalExcludeTax());
                        orderDTO.setState(order.getState());
                        orderDTO.setComment(order.getComment());
                        // Ne pas inclure le customer ici pour éviter la récursivité
                        return orderDTO;
                    })
                    .collect(Collectors.toList());

            customerDTO.setOrders(orderWithDTO);

            return customerDTO;
        }).collect(Collectors.toList());
    }

    // Get customer par l'ID
    public Optional<CustomerDTO> getCustomerById(Long id) {
        Optional<CustomerModel> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            CustomerModel customer = optionalCustomer.get();
            CustomerDTO customerDTO = new CustomerDTO();

            // Remplissage des propriétés de CustomerDTO
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

            // Conversion des commandes sans inclure le customer
            List<OrderDTO> orderWithDTO = customer.getOrder().stream()
                    .map(order -> {
                        OrderDTO orderDTO = new OrderDTO();
                        orderDTO.setId(order.getId());
                        orderDTO.setServiceType(order.getServiceType());
                        orderDTO.setTva(order.getTva());
                        orderDTO.setNbDays(order.getNbDays());
                        orderDTO.setTotalExcludeTax(order.getTotalExcludeTax());
                        orderDTO.setState(order.getState());
                        orderDTO.setComment(order.getComment());
                        // Ne pas inclure le customer dans l'OrderDTO pour éviter la récursivité
                        return orderDTO;
                    })
                    .collect(Collectors.toList());

            // Assigner les commandes au CustomerDTO
            customerDTO.setOrders(orderWithDTO);

            // Retourner le CustomerDTO encapsulé dans un Optional
            return Optional.of(customerDTO);
        } else {
            return Optional.empty();
        }
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

