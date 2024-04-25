package com.example.cookscorner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Import(MultipartAutoConfiguration.class)
public class CooksCornerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooksCornerApplication.class, args);
    }

}
