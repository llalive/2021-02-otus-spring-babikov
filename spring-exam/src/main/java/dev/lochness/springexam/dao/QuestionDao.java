package dev.lochness.springexam.dao;

import dev.lochness.springexam.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getAllQuestions();

    List<Question> getQuestions(int numberOfQuestions);
}
