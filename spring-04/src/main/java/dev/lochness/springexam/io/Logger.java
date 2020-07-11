package dev.lochness.springexam.io;

public interface Logger {
    void error(Exception exception);

    void warn(Exception exception);
}
