package ru.mirtv.tickets.reporter;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.testcontainers.containers.MySQLContainer;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.mirtv.tickets.reporter.config.MonthlyReportJobConfig.MONTHLY_REPORT_JOB;
import static ru.mirtv.tickets.reporter.config.MonthlyReportJobConfig.OUTPUT_FILE_NAME;

@SpringBatchTest
@SpringBootTest
class ReportJobTests {

    @Rule
    public MySQLContainer mysql = new MySQLContainer<>();

    private static final String ACTUAL_REPORT_FILE = "actual-test-report.xlsx";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void testJob() throws Exception {
        var classLoader = ReportJobTests.class.getClassLoader();

        FileSystemResource actualResult = new FileSystemResource(ACTUAL_REPORT_FILE);

        Job job = jobLauncherTestUtils.getJob();
        Assertions.assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(MONTHLY_REPORT_JOB);

        JobParameters parameters = new JobParametersBuilder()
                .addString(OUTPUT_FILE_NAME, ACTUAL_REPORT_FILE)
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        Assertions.assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(actualResult.getFile()));
        assertAll(() -> {
            assertNotNull(workbook);
            assertEquals(3, workbook.getNumberOfSheets());
        });
    }

}
