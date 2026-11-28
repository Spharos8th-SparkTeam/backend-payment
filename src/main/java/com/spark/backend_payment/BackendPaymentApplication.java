package com.spark.backend_payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BackendPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendPaymentApplication.class, args);
	}

}
