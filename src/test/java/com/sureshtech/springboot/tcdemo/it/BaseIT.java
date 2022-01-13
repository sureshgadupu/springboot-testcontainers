package com.sureshtech.springboot.tcdemo.it;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class BaseIT {
	
	@Autowired
	protected TestRestTemplate testRestTemplate ;
	
		
	@Container
	private static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>(PostgreSQLContainer.IMAGE)
																		.withDatabaseName("testdb")
																		.withUsername("postgres")
																		.withPassword("secret")
																		.withInitScript("ddl.sql")
																		
																		;
	
	@DynamicPropertySource
	public static void properties(DynamicPropertyRegistry registry) {
		
		registry.add("spring.datasource.url", postgresDB::getJdbcUrl);		
		registry.add("spring.datasource.username", postgresDB::getUsername);
		registry.add("spring.datasource.password", postgresDB::getPassword);
		
	}

}
