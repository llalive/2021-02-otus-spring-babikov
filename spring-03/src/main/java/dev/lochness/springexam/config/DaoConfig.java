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

    private Resource questionsResource;

    @Value(value = "#{ new dev.lochness.springexam.io.CSVQuestionReader()}")
    private QuestionReader<CSVRecord> fileService;

    @Bean
    public QuestionDao questionDao(AppConfig appConfig) {
        String resourceName = userLanguage != null && userLanguage.equals("ru")
                ? "questions_ru_RU.csv"
                : "questions.csv";
        questionsResource = new ClassPathResource(resourceName);
        return new QuestionDaoCSV(questionsResource,
                appConfig.getNumberOfAnswers(),
                fileService);
    }
}
