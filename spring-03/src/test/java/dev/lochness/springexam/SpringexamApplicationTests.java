package dev.lochness.springexam;

import dev.lochness.springexam.config.AppConfig;
import dev.lochness.springexam.dao.QuestionDao;
import dev.lochness.springexam.io.ConsoleExamScanner;
import dev.lochness.springexam.service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@DisplayName("Application integration test")
@SpringBootTest
class SpringexamApplicationTests {

    @Autowired
    ExamService examService;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AppConfig config;
    @MockBean
    ConsoleExamScanner scanner;

    @BeforeEach
    void setUp() {
        when(scanner.readUserAnswer()).thenReturn(1);
    }

    @Test
    @DisplayName("exam passed with correct number of questions")
    void examShouldWalkThroughAllQuestions() {
        examService.startTest();
        Mockito.verify(scanner,
                Mockito.times(config.getNumberOfQuestions())).readUserAnswer();
    }

}
