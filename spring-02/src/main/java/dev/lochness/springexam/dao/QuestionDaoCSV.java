package dev.lochness.springexam.dao;

import dev.lochness.springexam.domain.Answer;
import dev.lochness.springexam.domain.Question;
import dev.lochness.springexam.io.QuestionReader;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionDaoCSV implements QuestionDao {

    private final List<Question> questions;

    private final Resource questionsResource;
    private final int numberOfAnswers;
    private final QuestionReader<CSVRecord> fileService;

    public QuestionDaoCSV(Resource questionsResource, int numberOfAnswers, QuestionReader fileService) {
        this.questionsResource = questionsResource;
        this.numberOfAnswers = numberOfAnswers;
        this.fileService = fileService;
        questions = new ArrayList<>();
        readQuestions();
    }

    @Override
    public List<Question> getAllQuestions() {
        return questions;
    }

    @Override
    public List<Question> getQuestions(int numberOfQuestions) {
        int randomNum = ThreadLocalRandom.current()
                .nextInt(0, questions.size() - numberOfQuestions);
        return questions.subList(randomNum, randomNum + numberOfQuestions);
    }

    private final void readQuestions() {
        Iterable<CSVRecord> records = fileService.readFromResource(questionsResource);
        for (CSVRecord record : records) {
            String text = record.get("Question");
            Integer correctNum = Integer.parseInt(record.get("Correct"));
            Answer[] answers = new Answer[numberOfAnswers];
            for (int i = 1; i <= numberOfAnswers; i++) {
                answers[i - 1] = new Answer(record.get("Answer" + i), i == correctNum);
            }
            questions.add(new Question(text, answers));
        }
    }
}
