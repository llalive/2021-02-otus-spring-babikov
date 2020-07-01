package dev.lochness.springexam.io;

import java.io.Closeable;

public interface ExamScanner<T> extends Closeable {
    T readUserAnswer();
}
