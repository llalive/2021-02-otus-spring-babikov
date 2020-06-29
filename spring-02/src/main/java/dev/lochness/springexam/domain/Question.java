package dev.lochness.springexam.domain;

public class Question {
    private final String text;
    private final Answer[] answers;

    public Question(String text, Answer[] answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public Answer[] getAnswers() {
        return answers;
    }
}
