package com.api.autonomo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.api.autonomo.properties.FileDepotProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({ FileDepotProperties.class })
public class OwnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnerApplication.class, args);
	}

}
