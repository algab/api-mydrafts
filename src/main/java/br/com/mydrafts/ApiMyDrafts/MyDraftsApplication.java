package br.com.mydrafts.ApiMyDrafts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MyDraftsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDraftsApplication.class, args);
	}

}