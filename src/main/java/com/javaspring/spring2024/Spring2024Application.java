package com.javaspring.spring2024;

import com.javaspring.spring2024.extern.YAMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Spring2024Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Spring2024Application.class);
        app.run();
    }

    public void run(String... args) {
    }

}
