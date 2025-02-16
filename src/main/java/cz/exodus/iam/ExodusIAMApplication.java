package cz.exodus.iam;

import cz.exodus.jsend.network.client.WebClientConfig;
import cz.exodus.jsend.network.exception.GlobalExceptionHandler;
import cz.exodus.jsend.network.rest.JSendResponseAdvice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Import({
		WebClientConfig.class,
		GlobalExceptionHandler.class,
		JSendResponseAdvice.class
})
public class ExodusIAMApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExodusIAMApplication.class, args);
	}

}
