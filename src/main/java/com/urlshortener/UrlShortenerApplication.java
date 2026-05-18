package com.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application Entry Point.
 *
 * @SpringBootApplication is three annotations in one:
 *   1. @Configuration      — this class can define Spring beans
 *   2. @EnableAutoConfiguration — Spring auto-configures based on classpath
 *   3. @ComponentScan      — scans com.urlshortener.* for @Component, @Service etc.
 *
 * Rule: NEVER put any business logic here.
 * This class has exactly one job — start the application.
 *
 * Interview: "What does @SpringBootApplication do?"
 * Answer the three annotations above — most candidates just say "it starts the app."
 */
@SpringBootApplication
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}
