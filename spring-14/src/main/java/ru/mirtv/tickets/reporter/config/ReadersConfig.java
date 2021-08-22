package ru.mirtv.tickets.reporter.config;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import ru.mirtv.tickets.reporter.model.external.TicketsRecord;
import ru.mirtv.tickets.reporter.model.internal.ItemsFilter;
import ru.mirtv.tickets.reporter.model.internal.ReportType;
import ru.mirtv.tickets.reporter.utils.FilterUtils;

import javax.sql.DataSource;

@Configuration
public class ReadersConfig {

    @Value("${job.reports.filter}")
    private String filter;

    @Bean
    public ItemReader<TicketsRecord> assembliesReader(DataSource dataSource) {
        String query = new StringBuilder()
                .append("select a.start_date as startDate, a.end_date as endDate, ")
                .append("       p.program as program, p.code as code, ass.name as object ")
                .append("from assembly_task a ")
                .append("left join assembly_task_programs p on p.id = a.program_id ")
                .append("left join assembly ass on ass.id = a.assembly_id ")
                .append("where a.status_id = 2 ")
                .append(FilterUtils.getFilterByNameAndReportType(ItemsFilter.from(filter), ReportType.ASSEMBLIES))
                .toString();
        return new JdbcCursorItemReaderBuilder<TicketsRecord>()
                .name("assemblyReportReader")
                .dataSource(dataSource)
                .sql(query)
                .rowMapper(new BeanPropertyRowMapper<>(TicketsRecord.class))
                .build();
    }

    @Bean
    public ItemReader<TicketsRecord> shootingSetsReader(DataSource dataSource) {
        String query = new StringBuilder()
                .append("select s.send_time as startDate, s.return_time as endDate, ")
                .append("p.program as program, p.desc as code, sets.name as object ")
                .append("from shootings s ")
                .append("left join programs p on s.program_id = p.id ")
                .append("left join shooting_set ss on ss.shooting_id = s.id ")
                .append("left join sets on sets.id = ss.set_id ")
                .append("where (s.status_id = 2 or s.status_id = 3) and sets.name is not null ")
                .append(FilterUtils.getFilterByNameAndReportType(ItemsFilter.from(filter), ReportType.SHOOTING_SETS))
                .append("order by send_time asc ")
                .toString();
        return new JdbcCursorItemReaderBuilder<TicketsRecord>()
                .name("shootingSetsReader")
                .dataSource(dataSource)
                .sql(query)
                .rowMapper(new BeanPropertyRowMapper<>(TicketsRecord.class))
                .build();
    }

    @Bean
    public ItemReader<TicketsRecord> carsShootingsReader(DataSource dataSource) {
        String query = new StringBuilder()
                .append("select s.send_time as startDate, s.complete_time as endDate, ")
                .append("concat_ws(\" \", p.shortname, c.number) as object, pr.program as program, pr.desc as code ")
                .append("from shooting_cars ")
                .append("left join shootings s on shooting_cars.shooting_id = s.id ")
                .append("left join cars c on shooting_cars.car_id = c.id ")
                .append("left join shooting_peoples sp on s.id = sp.shooting_id ")
                .append("left join peoples p on sp.people_id = p.id ")
                .append("left join programs pr on s.program_id = pr.id ")
                .append("where s.status_id = 3 and p.role_id = 6 ")
                .append(FilterUtils.getFilterByNameAndReportType(ItemsFilter.from(filter), ReportType.CARS))
                .append("and s.send_time is not null and s.complete_time is not null ")
                .append("order by send_time asc")
                .toString();
        return new JdbcCursorItemReaderBuilder<TicketsRecord>()
                .name("carsShootingsReader")
                .dataSource(dataSource)
                .sql(query)
                .rowMapper(new BeanPropertyRowMapper<>(TicketsRecord.class))
                .build();
    }

}
