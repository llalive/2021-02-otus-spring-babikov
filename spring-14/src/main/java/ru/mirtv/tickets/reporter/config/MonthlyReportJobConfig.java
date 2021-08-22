package ru.mirtv.tickets.reporter.config;

import org.slf4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import ru.mirtv.tickets.reporter.model.batch.ReportItem;
import ru.mirtv.tickets.reporter.model.external.TicketsRecord;
import ru.mirtv.tickets.reporter.services.ReportService;
import ru.mirtv.tickets.reporter.writers.ReportWriter;

import java.util.List;

@Configuration
public class MonthlyReportJobConfig {

    public static final String OUTPUT_FILE_NAME = "outputFileName";
    public static final String MONTHLY_REPORT_JOB = "MonthlyReportJob";
    protected static final Logger log = org.slf4j.LoggerFactory.getLogger(MonthlyReportJobConfig.class);

    @Value("${job.reports.chunksize:1000}")
    protected int chunkSize;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    protected Step buildStep(String stepName,
                             ItemReader<TicketsRecord> reader,
                             ReportWriter writer,
                             ItemProcessor<TicketsRecord, ReportItem> processor) {
        return stepBuilderFactory.get(stepName)
                .<TicketsRecord, ReportItem>chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(buildReaderListener())
                .listener(buildWriterListener(writer))
                .listener(buildItemProcessorListener())
                .listener(buildChunkSelectorListener())
                .build();
    }

    @Bean
    public Step assembliesReport(@Qualifier("assembliesReader") ItemReader<TicketsRecord> reader,
                                 ReportWriter writer,
                                 ItemProcessor<TicketsRecord, ReportItem> itemProcessor) {
        return buildStep("assembliesReport", reader, writer, itemProcessor);
    }

    @Bean
    public Step setsShootingsReport(@Qualifier("shootingSetsReader") ItemReader<TicketsRecord> reader,
                                    ReportWriter writer,
                                    ItemProcessor<TicketsRecord, ReportItem> itemProcessor) {
        return buildStep("setsShootingsReport", reader, writer, itemProcessor);
    }

    @Bean
    public Step carsShootingsReport(@Qualifier("carsShootingsReader") ItemReader<TicketsRecord> reader,
                                    ReportWriter writer,
                                    ItemProcessor<TicketsRecord, ReportItem> itemProcessor) {
        return buildStep("carsShootingsReport", reader, writer, itemProcessor);
    }

    @Bean
    public Job doAssemblyReport(@Qualifier("assembliesReport") Step assemblyReport,
                                @Qualifier("setsShootingsReport") Step setsShootingsReport,
                                @Qualifier("carsShootingsReport") Step carsShootingsReport,
                                ReportWriter writer,
                                JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get(MONTHLY_REPORT_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(assemblyReport)
                .next(setsShootingsReport)
                .next(carsShootingsReport)
                .end()
                .listener(buildJobExecutionListener())
                .build();
    }

    @StepScope
    @Bean
    public ItemWriter<ReportItem> writer() {
        return new ReportWriter();
    }

    @Bean
    public ItemProcessor<TicketsRecord, ReportItem> processor(ReportService reportService) {
        return reportService::transform;
    }

    protected ChunkListener buildChunkSelectorListener() {
        return new ChunkListener() {
            public void beforeChunk(@NonNull ChunkContext chunkContext) {
                log.info("Начало пачки");
            }

            public void afterChunk(@NonNull ChunkContext chunkContext) {
                log.info("Конец пачки");
            }

            public void afterChunkError(@NonNull ChunkContext chunkContext) {
                log.info("Ошибка пачки");
            }
        };
    }

    protected ItemProcessListener<TicketsRecord, ReportItem> buildItemProcessorListener() {
        return new ItemProcessListener<TicketsRecord, ReportItem>() {
            public void beforeProcess(TicketsRecord o) {
                log.info("Начало обработки");
            }

            public void afterProcess(@NonNull TicketsRecord o, ReportItem o2) {
                log.info("Конец обработки");
            }

            @Override
            public void onProcessError(TicketsRecord ticketsRecord, Exception e) {
                log.info("Ошибка обработки", e);
            }
        };
    }

    protected ItemWriteListener<ReportItem> buildWriterListener(ReportWriter writer) {
        return new ItemWriteListener<>() {
            public void beforeWrite(@NonNull List list) {
                writer.open(new ExecutionContext());
                log.info("Начало записи");
            }

            public void afterWrite(@NonNull List list) {
                log.info("Конец записи");
            }

            public void onWriteError(@NonNull Exception e, @NonNull List list) {
                log.error("Ошибка записи", e);
            }
        };
    }

    protected ItemReadListener<TicketsRecord> buildReaderListener() {
        return new ItemReadListener<>() {
            public void beforeRead() {
                log.info("Начало чтения");
            }

            public void afterRead(@NonNull TicketsRecord ticketsRecord) {
                log.info("Запись прочитана: {}", ticketsRecord);
            }

            public void onReadError(@NonNull Exception e) {
                log.error("Ошибка чтения: ", e);
            }
        };
    }

    protected JobExecutionListener buildJobExecutionListener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(@NonNull JobExecution jobExecution) {
                log.info("Начало выгрузки: {}", jobExecution);
            }

            @Override
            public void afterJob(@NonNull JobExecution jobExecution) {
                log.info("Конец выгрузки: {}", jobExecution);
            }
        };
    }
}
