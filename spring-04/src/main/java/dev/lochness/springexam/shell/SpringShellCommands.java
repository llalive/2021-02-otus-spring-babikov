package dev.lochness.springexam.shell;

import dev.lochness.springexam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@RequiredArgsConstructor
@ShellComponent
public class SpringShellCommands {

    private final ExamService service;

    private String student;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Anonimous") String studentName) {
        this.student = studentName;
        return String.format("Student account: %s", studentName);
    }

    @ShellMethod(value = "Start test", key = {"t", "test", "start", "s", "st"})
    @ShellMethodAvailability(value = "isStartTestMethodAvailable")
    public void startTest() {
        service.startTest();
    }

    private Availability isStartTestMethodAvailable() {
        return student == null
                ? Availability.unavailable("Please, introduce yourself before test")
                : Availability.available();
    }
}
