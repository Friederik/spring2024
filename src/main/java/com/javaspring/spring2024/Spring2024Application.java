package com.javaspring.spring2024;

import com.javaspring.spring2024.extern.YAMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Spring2024Application implements CommandLineRunner {

    @Autowired
    private YAMLConfig myConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Spring2024Application.class);
        app.run();
    }

    /**
     * Печатает текущую конфигурацию
     * @param args incoming main method arguments
     */
    public void run(String... args) {
        System.out.println("using url: " + myConfig.getUrl());
        System.out.println("username: " + myConfig.getName());
        System.out.println("password: " + myConfig.getPassword());
        System.out.println("driver-class-name: " + myConfig.getDriverClassName());
    }

}
