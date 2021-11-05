package com.study.microservice.currencycontrollerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyControllerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyControllerServiceApplication.class, args);
	}

}
