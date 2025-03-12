package com.projetos.backbibliotecafinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BackBibliotecaFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackBibliotecaFinalApplication.class, args);
    }

}
