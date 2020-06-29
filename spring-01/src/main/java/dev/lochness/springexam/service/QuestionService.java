package dev.lochness.springexam.service;

import dev.lochness.springexam.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getQuestions();

    boolean checkAnswer(Question question, int answer);
}
