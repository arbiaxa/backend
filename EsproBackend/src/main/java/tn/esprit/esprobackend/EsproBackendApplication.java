package tn.esprit.esprobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EsproBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsproBackendApplication.class, args);
	}


	//ajout le code de cors pour autoriser l access

/*
//spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username="Achref.kacem@hotmail.com"
spring.mail.password="Arbiasassi20002001*"
spring.mail.properties.auth=true
spring.mail.properties.starttls.enable=true*/


}
