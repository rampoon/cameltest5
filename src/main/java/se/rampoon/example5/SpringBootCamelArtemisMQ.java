package se.rampoon.example5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Test of Camel by using Spring boot. Consuming from ArtemisMQ, producing a file.
 *
 */
@SpringBootApplication
public class SpringBootCamelArtemisMQ {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCamelArtemisMQ.class, args);
    }
}
