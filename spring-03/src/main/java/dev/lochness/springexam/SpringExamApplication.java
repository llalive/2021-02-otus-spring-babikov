package dev.lochness.springexam;

import dev.lochness.springexam.config.AppConfig;
import dev.lochness.springexam.service.ExamService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class SpringExamApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(SpringExamApplication.class, args);
        ExamService service = context.getBean(ExamService.class);
        service.startTest();
    }

}
