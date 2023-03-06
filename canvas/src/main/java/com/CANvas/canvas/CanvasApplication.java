package com.CANvas.canvas;

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
public class CanvasApplication {

	@PostMapping("/getFile")
	public int create(@RequestBody String message) {
		System.out.println("hello there");
		return 0;
	}

	public static void main(String[] args) {
		System.out.println("hello");
		SpringApplication.run(CanvasApplication.class, args);
	}

}
