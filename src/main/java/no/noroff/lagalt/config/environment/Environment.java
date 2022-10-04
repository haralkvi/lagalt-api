package no.noroff.lagalt.config.environment;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Environment {

    public static String DB_URL = "jdbc:postgresql://localhost:5432/lagalt_db";
    public static String DB_USERNAME = "postgres";
    public static String DB_PASSWORD = "postgres";

}
