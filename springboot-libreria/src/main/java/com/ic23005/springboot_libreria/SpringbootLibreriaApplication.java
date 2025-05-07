package com.ic23005.springboot_libreria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootLibreriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLibreriaApplication.class, args);
        System.out.println("Aplicación Spring Boot Librería iniciada!");
        System.out.println("Las tablas deberían haberse creado/actualizado en PostgreSQL si ddl-auto está configurado.");
    }

}
