package dev.lochness.springexam.shell;

import dev.lochness.springexam.service.ExamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Class SpringShellCommands ")
@SpringBootTest(properties = { "spring.shell.interactive.enabled=false" })
class SpringShellCommandsTest {

    @MockBean
    private ExamService service;

    @Autowired
    private Shell shell;

    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_START_TEST = "test";

    @DisplayName("will answer user name before test")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldRequestNameBeforeTest() {
        assertThat(shell.evaluate(() -> COMMAND_START_TEST))
                .isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("will start test on command")
    @Test
    void startTest() {
        shell.evaluate(() -> COMMAND_LOGIN);
        shell.evaluate(() -> COMMAND_START_TEST);
        verify(service, times(1)).startTest();
    }
}