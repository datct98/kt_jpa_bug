package com.example.kt_jpa.service;

import com.example.kt_jpa.model.dto.OrderDTO;
import com.example.kt_jpa.model.dto.ProductDTO;
import com.example.kt_jpa.model.entities.*;
import com.example.kt_jpa.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    @Override
    public Order createOrder(OrderDTO dto) {
        Order order = new Order();
        ModelMapper mapper = new ModelMapper();
        Shiper shiper = shipperRepository.findById(dto.getShipperId()).orElse(null);
        if(shiper == null){
            System.out.println("Not found shipper with id: "+dto.getShipperId());
            return null;
        }
        Customer customer = customerRepository.findByPhone(dto.getCustomer().getPhone());
        if(customer != null)
            order.setCustomer(customer);
        else {
            Wallet wallet = new Wallet();
            wallet.setAccountNumber(dto.getCustomer().getWallet().getAccountNumber());
            wallet.setBalance(dto.getCustomer().getWallet().getBalance());
            walletRepository.save(wallet);
            Customer customerNew = mapper.map(dto.getCustomer(), Customer.class);
            customerNew.setWallet(wallet);
            customerRepository.save(customerNew);
            order.setCustomer(customerNew);
        }
        order.setAddress(dto.getAddress());
        order.setStatus("Waiting processing...");
        Date currentDate = new Date(System.currentTimeMillis());
        System.out.println("Current: "+ currentDate);
        order.setTimeOrder(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 7);
        currentDate = calendar.getTime();
        System.out.println("Estimate: "+ currentDate);
        order.setEstimateTime(currentDate);

        order.setShiper(shiper);
        order.setPrice(totalPrice(dto.getProducts()));

        //order.setProducts(dto.getProducts());
        orderRepository.save(order);
        Set<Product> products = dto.getProducts().stream().map(productDTO -> mapper.map(productDTO, Product.class)).collect(Collectors.toSet());
        for (Product p:products) {
            p.setOrder(order);
        }
        productRepository.saveAll(products);
        return order;
    }

    @Override
    public String updateOrder(OrderDTO dto) {
        return null;
    }

    private double totalPrice(Set<ProductDTO> products){

        // Cách 1
        /*
        double weight =0;
        for (Product p: products) {
            weight+= p.getWeight();
        }*/
        // Cách 2: sử dụng stream
        double weight= products.stream().mapToDouble(ProductDTO::getWeight).sum();
        return weight<= 10?15:  weight <= 20? 20 : 30;
    }
}
