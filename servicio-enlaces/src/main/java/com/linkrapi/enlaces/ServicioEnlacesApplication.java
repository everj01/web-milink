package com.linkrapi.enlaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServicioEnlacesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioEnlacesApplication.class, args);
    }
}
