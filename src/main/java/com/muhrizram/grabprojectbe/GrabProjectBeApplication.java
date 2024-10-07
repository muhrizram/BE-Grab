package com.muhrizram.grabprojectbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.muhrizram.config.KafkaConsumer;

@Import(KafkaConsumer.class)
@SpringBootApplication
public class GrabProjectBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrabProjectBeApplication.class, args);
	}

}
