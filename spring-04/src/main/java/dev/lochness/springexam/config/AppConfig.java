package dev.lochness.springexam.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class AppConfig {

    private int numberOfQuestions;
    private int numberOfAnswers;

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }
}
