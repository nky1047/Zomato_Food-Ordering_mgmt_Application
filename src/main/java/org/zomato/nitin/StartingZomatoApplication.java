package org.zomato.nitin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("org.zomato.nitin.Repositories") // Specify the package for your repositories
public class StartingZomatoApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartingZomatoApplication.class, args);
        System.out.println("\tZomato Application Started!");
    }
}