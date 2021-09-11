package dev.lochness.library;

import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseEmbedDatabaseTest {

    @Container
    public static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("library")
            .withUsername("postgres")
            .withPassword("postgres");

}
