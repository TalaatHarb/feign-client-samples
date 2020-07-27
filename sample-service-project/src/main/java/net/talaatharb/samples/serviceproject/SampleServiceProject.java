package net.talaatharb.samples.serviceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SampleServiceProject {

	public static void main(String[] args) {		
		SpringApplication.run(SampleServiceProject.class, args);
	}

}
