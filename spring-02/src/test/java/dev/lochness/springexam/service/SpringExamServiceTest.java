package dev.lochness.springexam.service;

import dev.lochness.springexam.dao.QuestionDao;
import dev.lochness.springexam.domain.Answer;
import dev.lochness.springexam.domain.Question;
import dev.lochness.springexam.io.ExamPrinter;
import dev.lochness.springexam.io.ExamScanner;
import dev.lochness.springexam.io.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("Class SpringExamService")
class SpringExamServiceTest {
    @Mock
    private QuestionDao dao;
    @Mock
    private ExamPrinter printer;
    @Mock
    private ExamScanner scanner;
    @Mock
    private Logger logger;
    private SpringExamService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", new Answer[]{
                new Answer("Answer 1", true),
                new Answer("Answer 2", false)
        }));
        questions.add(new Question("Question 2", new Answer[]{
                new Answer("Answer 1", false),
                new Answer("Answer 2", true)
        }));
        given(dao.getQuestions(eq(2))).willReturn(questions);
        when(scanner.readUserAnswer()).thenReturn(1);
        service = new SpringExamService(dao,
                2,
                printer,
                scanner,
                logger);
    }

    @Test
    @DisplayName("answers checked correctly")
    void testShouldReturnCorrectScore() {
        service.startTest();
        assertEquals(1, service.getCorrectAnswersCount());
    }

}
