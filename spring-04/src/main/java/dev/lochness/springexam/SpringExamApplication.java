package dev.lochness.springexam;

import dev.lochness.springexam.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class SpringExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringExamApplication.class, args);
    }

}
