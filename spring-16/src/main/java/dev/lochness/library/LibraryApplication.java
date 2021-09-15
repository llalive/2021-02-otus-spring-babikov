package dev.lochness.library;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@EnableAutoConfiguration
@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(LibraryApplication.class, args);
        Console.main(args);
    }

}
