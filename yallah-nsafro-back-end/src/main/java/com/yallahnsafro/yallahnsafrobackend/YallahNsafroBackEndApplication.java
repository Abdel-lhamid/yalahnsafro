package com.yallahnsafro.yallahnsafrobackend;

import com.yallahnsafro.yallahnsafrobackend.shared.SpringApplicationContext;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class YallahNsafroBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(YallahNsafroBackEndApplication.class, args);
		System.out.println("test working");
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}



}
