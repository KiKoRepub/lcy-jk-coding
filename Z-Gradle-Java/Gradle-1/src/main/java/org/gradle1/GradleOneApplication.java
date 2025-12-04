package org.gradle1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GradleOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(GradleOneApplication.class, args);
        System.out.println("Hello world!");
    }
}