package dev.lochness.springexam.config;

import dev.lochness.springexam.dao.QuestionDao;
import dev.lochness.springexam.dao.QuestionDaoCSV;
import dev.lochness.springexam.io.QuestionReader;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class DaoConfig {

    @Value("#{ systemProperties['user.language'] }")
    private String userLanguage;
    @Value("#{ systemProperties['user.country'] }")
    private String userCountry;

    private Resource questionsResource;

    @Value(value = "#{ new dev.lochness.springexam.io.CSVQuestionReader()}")
    private QuestionReader<CSVRecord> fileService;

    @Bean
    public QuestionDao questionDao(AppConfig appConfig) {
        questionsResource = new ClassPathResource(
                String.format("questions_%s_%s.csv", userLanguage, userCountry));
        if(!questionsResource.exists())
            questionsResource = new ClassPathResource("questions.csv");
        return new QuestionDaoCSV(questionsResource,
                appConfig.getNumberOfAnswers(),
                fileService);
    }
}
