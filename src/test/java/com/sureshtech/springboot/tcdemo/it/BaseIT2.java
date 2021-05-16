package com.sureshtech.springboot.tcdemo.it;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = BaseIT2.TestEnvInitializer.class)
public class BaseIT2 {
	
	@Autowired
	protected TestRestTemplate testRestTemplate ;
	
		
	@Container
	private static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:13.2")
																			.withDatabaseName("testdb")
																			.withUsername("postgres")
																			.withPassword("secret")
																			.withInitScript("ddl.sql");
																		
																		
	
	static class TestEnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			 TestPropertyValues values = TestPropertyValues.of(
				        "spring.datasource.url=" + postgresDB.getJdbcUrl(),
				        "spring.datasource.password=" + postgresDB.getPassword(),
				        "spring.datasource.username=" + postgresDB.getUsername()
				      );
			 values.applyTo(applicationContext);
			
		}
		
	}

}
