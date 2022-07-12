package com.mescobar.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import org.springframework.messaging.Message;

import com.mescobar.notification.service.EmailSender;

import java.util.function.Consumer;

@SpringBootApplication
@EnableEurekaClient
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	
	@Bean
    public Consumer<Message<String>> notificationEventSupplier() {
      return stringMessage -> new EmailSender().sendEmail(stringMessage.getPayload());
    }

}
