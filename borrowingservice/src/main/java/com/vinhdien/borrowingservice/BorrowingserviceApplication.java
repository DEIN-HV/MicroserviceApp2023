package com.vinhdien.borrowingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
public class BorrowingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowingserviceApplication.class, args);
	}

}
