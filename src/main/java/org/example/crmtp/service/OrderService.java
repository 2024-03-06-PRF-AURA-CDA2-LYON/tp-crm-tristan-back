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
import java.util.stream.Collectors;

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
        return orders.stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setServiceType(order.getServiceType());
            orderDTO.setTva(order.getTva());
            orderDTO.setNbDays(order.getNbDays());
            orderDTO.setTotalExcludeTax(order.getTotalExcludeTax());
            orderDTO.setState(order.getState());
            orderDTO.setComment(order.getComment());

            // Récupérer uniquement l'ID et le companyName du customer
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(order.getCustomer().getId());
            customerDTO.setCompanyName(order.getCustomer().getCompanyName());

            orderDTO.setCustomer(customerDTO); // Assigner le customerDTO

            return orderDTO;
        }).collect(Collectors.toList());
    }

    // Recupp order par l'ID
    public Optional<OrderDTO> getOrderById(Long id) {
        Optional<OrderModel> orderOpt = orderRepository.findById(id);
        return orderOpt.map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setServiceType(order.getServiceType());
            orderDTO.setTva(order.getTva());
            orderDTO.setNbDays(order.getNbDays());
            orderDTO.setTotalExcludeTax(order.getTotalExcludeTax());
            orderDTO.setState(order.getState());
            orderDTO.setComment(order.getComment());

            // Récupérer uniquement l'ID et le companyName du customer
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(order.getCustomer().getId());
            customerDTO.setCompanyName(order.getCustomer().getCompanyName());

            orderDTO.setCustomer(customerDTO); // Assigner le customerDTO

            return orderDTO;
        });
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

