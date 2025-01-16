package org.zomato.nitin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories("com.example.zomato.repository") // Specify the package for your repositories
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Zomato!");
        SpringApplication.run(Main.class, args);
    }
}