package dev.lochness.springexam.io;

import dev.lochness.springexam.domain.Question;

public interface ExamPrinter {
    void printQuestion(Question question);

    void printResult(int correct, int numberOfQuestions);
}
