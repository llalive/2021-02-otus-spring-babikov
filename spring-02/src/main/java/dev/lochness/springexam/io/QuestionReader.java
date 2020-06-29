package dev.lochness.springexam.io;

import org.springframework.core.io.Resource;

public interface QuestionReader<T> {
    Iterable<T> readFromResource(Resource resource);
}