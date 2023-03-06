package com.CANvas.can;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class CanApplication<TomcatConnectorCustomizer> {
	private int maxUploadSizeInMb = 100 * 1024 * 1024; // 10 MB



	public static void main(String[] args) throws Exception {
		SpringApplication.run(CanApplication.class, args);
	}

	}



