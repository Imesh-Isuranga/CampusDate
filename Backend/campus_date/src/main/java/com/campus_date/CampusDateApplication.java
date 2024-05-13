package com.campus_date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CampusDateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusDateApplication.class, args);
    }

}
