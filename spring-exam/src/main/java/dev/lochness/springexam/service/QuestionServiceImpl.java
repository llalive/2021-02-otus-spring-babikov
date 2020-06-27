package dev.lochness.springexam.service;

import dev.lochness.springexam.dao.QuestionDao;
import dev.lochness.springexam.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final int numberOfQuestions;

    public QuestionServiceImpl(QuestionDao dao, int numberOfQuestions) {
        this.dao = dao;
        this.numberOfQuestions = numberOfQuestions;
    }

    @Override
    public List<Question> getQuestions() {
        return dao.getQuestions(numberOfQuestions);
    }

    @Override
    public boolean checkAnswer(Question question, int answer) {
        if (question.getAnswers().length < answer || answer < 0) {
            return false;
        }
        return question.getAnswers()[answer - 1].isCorrect();
    }
}
