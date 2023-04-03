package io.koatech.aerosage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"io.koatech.aerosage.services", "io.koatech.aerosage.controllers", "io.koatech.aerosage.repositories", "io.koatech.aerosage.models"})
public class AeroSageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AeroSageApplication.class, args);
    }

}
