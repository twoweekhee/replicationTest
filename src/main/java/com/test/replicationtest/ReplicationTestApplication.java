package com.test.replicationtest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "replicationTest", version = "v1", description = "iqooca intern subject"))
public class ReplicationTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReplicationTestApplication.class, args);
	}

}
