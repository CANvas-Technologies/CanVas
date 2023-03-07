package org.canvas.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootEntrypoint<TomcatConnectorCustomizer> {
    public static void main(String[] args) throws Exception {
        // Check for required env vars
        final String[] vars = {
            "CANVAS_DB_HOST", "CANVAS_DB_DATABASE_NAME", "CANVAS_DB_USER", "CANVAS_DB_PASSWORD"
        };

        boolean missingEnvVars = false;
        for (String var : vars) {
            if (System.getenv(var) == null) {
                System.err.println("Missing required environment variable '" + var + "'");
                missingEnvVars = true;
            }
        }

        if (missingEnvVars) {
            System.exit(-2);
        }

        SpringApplication.run(SpringBootEntrypoint.class, args);
    }
}
