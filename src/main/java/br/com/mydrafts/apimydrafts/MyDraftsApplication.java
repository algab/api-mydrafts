package br.com.mydrafts.apimydrafts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MyDraftsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDraftsApplication.class, args);
	}

}
