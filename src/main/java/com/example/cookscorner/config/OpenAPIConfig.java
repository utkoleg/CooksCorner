package com.example.cookscorner.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info (
                contact = @Contact(
                        name = "utkoleg",
                        email = "utkaleg@gmail.com"
                ),
                description = "OpenAPI documentation",
                title = "OpenAPI specification - utkoleg",
                version = "1.0",
                license = @License(
                        name = "utkoleg licence"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Droplet ENV",
                        url = "http://165.232.124.153:8080"
                )
        }
)
public class OpenAPIConfig {
}
