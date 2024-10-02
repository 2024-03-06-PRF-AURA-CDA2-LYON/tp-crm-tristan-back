package org.example.crmtp.service;

import org.example.crmtp.model.CustomerModel;
import org.example.crmtp.model.OrderModel;
import org.example.crmtp.repository.CustomerRepository;
import org.example.crmtp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
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

