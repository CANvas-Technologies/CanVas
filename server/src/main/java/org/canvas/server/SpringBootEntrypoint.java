package org.canvas.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootEntrypoint<TomcatConnectorCustomizer> {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootEntrypoint.class, args);
    }
}
