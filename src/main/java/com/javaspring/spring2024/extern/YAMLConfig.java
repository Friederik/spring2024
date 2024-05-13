package com.javaspring.spring2024.extern;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Параметры конфигурации
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {

    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String driverClassName;
}
