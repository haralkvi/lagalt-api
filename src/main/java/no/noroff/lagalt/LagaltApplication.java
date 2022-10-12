package no.noroff.lagalt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class LagaltApplication {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Lagalt API";
    }

    public static void main(String[] args) {

        SpringApplication.run(LagaltApplication.class, args);
    }

}
