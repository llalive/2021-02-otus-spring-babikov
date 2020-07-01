package dev.lochness.springexam.io;

import dev.lochness.springexam.domain.Answer;
import dev.lochness.springexam.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ConsoleExamPrinter implements ExamPrinter {

    private final Locale userLocale;
    private final MessageSource messageSource;

    @Autowired
    public ConsoleExamPrinter(MessageSource messageSource,
                              @Value("#{ systemProperties['user.language'] }") String userLanguage) {
        this.messageSource = messageSource;
        userLocale = userLanguage != null
                ? Locale.forLanguageTag(userLanguage)
                : Locale.getDefault();
    }

    @Override
    public void printQuestion(Question question) {
        System.out.println(question.getText());
        for (int i = 0; i < question.getAnswers().length; i++) {
            printAnswer(i, question.getAnswers()[i]);
        }
    }

    @Override
    public void printResult(int correct, int numberOfQuestions) {
        System.out.println(messageSource.getMessage("result.output",
                new String[]{String.valueOf(correct), String.valueOf(numberOfQuestions)},
                userLocale));
    }

    private void printAnswer(int id, Answer answer) {
        System.out.printf("[%d] %s\n", id + 1, answer.getText());
    }
}
