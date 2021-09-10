package dev.lochness.library.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private String port;

    @SneakyThrows
    @Override
    public Health health() {
        HttpUriRequest request = new HttpGet("http://" + address + ":" + port + "/");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        return statusCode == HttpStatus.SC_OK
                ? Health.up().status(Status.UP).withDetail("app", "Application is up").build()
                : Health.down().status(Status.DOWN).withDetail("app", "Application is down").build();
    }
}
