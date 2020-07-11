package dev.lochness.springexam.io;

import org.springframework.stereotype.Service;

@Service
public class ConsoleLogger implements Logger {

    @Override
    public void error(Exception exception) {
        System.out.println("Error: " + exception.getMessage());
    }

    @Override
    public void warn(Exception exception) {
        System.out.println("Warning: " + exception.getMessage());
    }
}
