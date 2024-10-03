package org.example.crmtp.service;

import org.example.crmtp.dto.CustomerDTO;
import org.example.crmtp.dto.OrderDTO;
import org.example.crmtp.model.CustomerModel;
import org.example.crmtp.model.OrderModel;
import org.example.crmtp.repository.CustomerRepository;
import org.example.crmtp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    // Récupérer toutes les order
    public List<OrderDTO> getAllOrders() {
        List<OrderModel> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (OrderModel order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setServiceType(order.getServiceType());
            orderDTO.setTva(order.getTva());
            orderDTO.setNbDays(order.getNbDays());
            orderDTO.setTotalExcludeTax(order.getTotalExcludeTax());
            orderDTO.setState(order.getState());
            orderDTO.setComment(order.getComment());


            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(order.getCustomer().getId());
            customerDTO.setCompanyName(order.getCustomer().getCompanyName());
            customerDTO.setFirstName(order.getCustomer().getFirstName());
            customerDTO.setLastName(order.getCustomer().getLastName());
            customerDTO.setEmail(order.getCustomer().getEmail());
            customerDTO.setPhoneNumber(order.getCustomer().getPhoneNumber());
            customerDTO.setAddress(order.getCustomer().getAddress());
            customerDTO.setZipCode(order.getCustomer().getZipCode());
            customerDTO.setCountry(order.getCustomer().getCountry());
            customerDTO.setCity(order.getCustomer().getCity());
            customerDTO.setState(order.getCustomer().getState());

            orderDTO.setCustomer(customerDTO);
            orderDTOs.add(orderDTO);
        }

        return orderDTOs;
    }


    // Récupérer une order par ID
    public Optional<OrderModel> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Créer une new order
    public OrderModel createOrder(OrderModel order, Long customerId) {
        CustomerModel customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        order.setCustomer(customer);
        return orderRepository.save(order);
    }

    // Update une order
    public OrderModel updateOrder(Long id, OrderModel updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setServiceType(updatedOrder.getServiceType());
            order.setTva(updatedOrder.getTva());
            order.setNbDays(updatedOrder.getNbDays());
            order.setTotalExcludeTax(updatedOrder.getTotalExcludeTax());
            order.setState(updatedOrder.getState());
            order.setComment(updatedOrder.getComment());
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Commande non trouvée"));
    }

    // Supprimer une order par l'ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

