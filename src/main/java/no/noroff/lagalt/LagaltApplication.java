package no.noroff.lagalt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LagaltApplication {

    public static void main(String[] args) {

        System.out.println(System.getenv("DATASOURCE_URL"));
        SpringApplication.run(LagaltApplication.class, args);
    }

}
