package dev.lochness.springexam.dao;

import dev.lochness.springexam.domain.Answer;
import dev.lochness.springexam.domain.Question;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionDaoCSV implements QuestionDao {

    private final List<Question> questions;
    private final Resource questionsResource;
    private final int numberOfAnswers;

    public QuestionDaoCSV(String questionsResourceName, int numberOfAnswers)
            throws IOException {
        this.questionsResource = new ClassPathResource(questionsResourceName);
        this.numberOfAnswers = numberOfAnswers;
        questions = new ArrayList<>();
        readQuestionsFromCSV();
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

    private final void readQuestionsFromCSV() throws IOException {
        try (Reader reader = new InputStreamReader(questionsResource.getInputStream())) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(";".charAt(0))
                    .withFirstRecordAsHeader().parse(reader);
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
}
