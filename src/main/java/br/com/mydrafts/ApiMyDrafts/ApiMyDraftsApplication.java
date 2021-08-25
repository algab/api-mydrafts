package br.com.mydrafts.ApiMyDrafts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiMyDraftsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMyDraftsApplication.class, args);
	}

}
