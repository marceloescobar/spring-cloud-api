package com.mescobar.order.controller;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mescobar.order.client.InventoryClient;
import com.mescobar.order.dto.OrderDto;
import com.mescobar.order.model.Order;
import com.mescobar.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private final StreamBridge streamBridge;
    
    
    @PostMapping
    public String placeOrder(@RequestBody OrderDto orderDto) {
    	Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
    	
    	Supplier<Boolean> booleanSupplier = () -> orderDto.getOrderLineItemsList()
                .stream()
                .allMatch(orderLineItems -> inventoryClient.checkStock(orderLineItems.getSkuCode()));
    	
    	 boolean isProductInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase());

    	
    	if (isProductInStock) {
            Order order = new Order();
            order.setOrderLineItems(orderDto.getOrderLineItemsList());
            order.setOrderNumber(UUID.randomUUID().toString());

            orderRepository.save(order);
            log.info("Sending Order Details to Notification Service");

            //Send the Notication via RabbitMQ
            streamBridge.send("notificationEventSupplier-out-0",
                    MessageBuilder.withPayload(order.getId()).build());
            
            
            return "Order Placed Successfull!";
        }else{
            return "Order Failed !, One of the products in the order is not in the stock!";
        }
    }
    
    private Boolean handleErrorCase() {
        System.out.println("fallback called !!");
        return false;
    }
    
}
