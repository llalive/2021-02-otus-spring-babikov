package dev.lochness.springexam.service;

import dev.lochness.springexam.dao.QuestionDao;
import dev.lochness.springexam.domain.Answer;
import dev.lochness.springexam.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionServiceImpl")
class QuestionServiceImplTest {

    @Mock
    private QuestionDao dao;
    private QuestionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ArrayList<Question> questions = new ArrayList<>();
        Answer[] answers = new Answer[2];
        answers[0] = new Answer("1", false);
        answers[1] = new Answer("2", true);
        questions.add(new Question("1", answers));
        questions.add(new Question("2", answers));
        given(dao.getQuestions(eq(2))).willReturn(questions);
        service = new QuestionServiceImpl(dao, 2);
    }

    @Test
    @DisplayName("init is correct")
    void shouldHaveCorrectConstructor() {
        assertEquals(service.getQuestions().size(), 2);
    }

    @Test
    @DisplayName("answers checked correctly")
    void shouldCheckAnswersCorrectly() {
        Question question = service.getQuestions().get(0);
        assertAll(
                () -> assertTrue(service.checkAnswer(question, 2)),
                () -> assertFalse(service.checkAnswer(question, 1))
        );
    }

}
