package dev.lochness.springexam.service;

import dev.lochness.springexam.config.AppConfig;
import dev.lochness.springexam.dao.QuestionDao;
import dev.lochness.springexam.domain.Question;
import dev.lochness.springexam.io.ExamPrinter;
import dev.lochness.springexam.io.ExamScanner;
import dev.lochness.springexam.io.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpringExamService implements ExamService {

    private final QuestionDao questionDao;
    private final int numberOfQuestions;
    private final ExamPrinter printer;
    private final ExamScanner<Integer> scanner;
    private int correctAnswersCount;
    private final Logger logger;

    @Autowired
    public SpringExamService(QuestionDao dao,
                             AppConfig appConfig,
                             ExamPrinter printer,
                             ExamScanner scanner,
                             Logger logger) {
        this.questionDao = dao;
        this.numberOfQuestions = appConfig.getNumberOfQuestions();
        this.printer = printer;
        this.scanner = scanner;
        this.logger = logger;
    }

    @Override
    public void startTest() {
        List<Question> questions = questionDao.getQuestions(numberOfQuestions);
        correctAnswersCount = 0;
        for (Question question : questions) {
            printer.printQuestion(question);
            if (checkAnswer(question, scanner.readUserAnswer())) {
                correctAnswersCount++;
            }
        }
        printer.printResult(correctAnswersCount, numberOfQuestions);
        try {
            scanner.close();
        } catch (IOException exception) {
            logger.error(exception);
        }
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    private boolean checkAnswer(Question question, int answer) {
        if (question.getAnswers().length < answer || answer < 0) {
            return false;
        }
        return question.getAnswers()[answer - 1].isCorrect();
    }
}