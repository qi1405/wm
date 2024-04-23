package com.crm.wm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class WmApplication {

	public static void main(String[] args) {
		SpringApplication.run(WmApplication.class, args);
	}

}
