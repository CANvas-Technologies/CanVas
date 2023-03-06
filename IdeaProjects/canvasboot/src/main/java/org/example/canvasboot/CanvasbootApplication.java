package org.example.canvasboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SpringBootApplication
@RestController


public class CanvasbootApplication {
	@PostMapping("/getPlayerById")
	public int create(@RequestBody String message) {
		System.out.println("TESTING");
		return 0;
	}

	public static void main(String[] args)
	{
		SpringApplication.run(CanvasbootApplication.class, args);
	}

}
