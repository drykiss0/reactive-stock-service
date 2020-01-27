package com.globallogic.reactivestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.globallogic")
@EnableMongoAuditing
@EnableReactiveMongoRepositories
public class ReactiveStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveStockApplication.class, args);
	}
}
