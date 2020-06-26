package dev.lochness.springexam;

import dev.lochness.springexam.domain.Answer;
import dev.lochness.springexam.domain.Question;
import dev.lochness.springexam.service.QuestionService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        List<Question> questions = service.getQuestions();
        int correct = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            for (Question question : questions) {
                System.out.println(question.getText());
                for (int i = 0; i < question.getAnswers().length; i++) {
                    System.out.printf("[%d] %s\n", i + 1, question.getAnswers()[i].getText());
                }
                int answer = scanner.nextInt();
                if (service.checkAnswer(question, answer)) {
                    correct++;
                }
            }
            System.out.println("Correct answers: " + correct + " of " + questions.size());
        }
    }
}