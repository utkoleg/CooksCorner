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
                        url = "http://localhost:9999"
                ),
                @Server(
                        description = "Droplet ENV",
                        url = "http://164.92.253.177:9999"
                )
        }
)
public class OpenAPIConfig {
}
