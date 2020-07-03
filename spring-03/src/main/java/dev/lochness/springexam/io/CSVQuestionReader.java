package dev.lochness.springexam.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class CSVQuestionReader implements QuestionReader<CSVRecord> {

    @Override
    public Iterable<CSVRecord> readFromResource(Resource resource) {
        Iterable records = null;
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            records = CSVFormat.EXCEL.withDelimiter(";".charAt(0))
                    .withFirstRecordAsHeader().parse(reader).getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
