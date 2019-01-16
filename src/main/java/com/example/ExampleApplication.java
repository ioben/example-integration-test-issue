package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.PrintStream;

@SpringBootApplication
public class ExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Bean
    ApplicationRunner application(Outputter output, Configuration config) {
        return args -> output.write(config.getOutput());
    }

    @Bean
    Outputter output() {
        return new PrintStreamOutputter(System.out);
    }


    @Bean
    @ConfigurationProperties("example")
    Configuration config() {
        return new Configuration();
    }

    @Data
    public static class Configuration {
        private String output;
    }


    public interface Outputter {
        void write(String message);
    }

    @AllArgsConstructor
    public static class PrintStreamOutputter implements Outputter {
        PrintStream output;

        public void write(String message) {
            output.println(message);
        }
    }
}
