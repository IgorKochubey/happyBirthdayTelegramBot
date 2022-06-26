package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import static java.util.Objects.nonNull;

@Slf4j
@SpringBootApplication
@EnableScheduling
@PropertySource(value = {"classpath:application.properties", "classpath:secret.properties"})
public class DemoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        if(nonNull(args)) {
            for (String arg : args) {
                System.out.println("arg = " + arg);
            }
        }
        SpringApplication.run(DemoApplication.class, args);
    }
}
