package dev.lochness.springexam.io;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Scanner;

@Service
public class ConsoleExamScanner implements ExamScanner<Integer> {

    private final Scanner in;

    public ConsoleExamScanner() {
        in = new Scanner(System.in);
    }

    @Override
    public Integer readUserAnswer() {
        return in.nextInt();
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}