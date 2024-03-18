package com.example.cookscorner.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {
    private String username;
    private String password;
}