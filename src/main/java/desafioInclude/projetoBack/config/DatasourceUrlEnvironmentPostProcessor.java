package desafioInclude.projetoBack.config;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DatasourceUrlEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTY_SOURCE_NAME = "normalizedDatasource";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String rawUrl = firstNonBlank(
                environment.getProperty("SPRING_DATASOURCE_URL"),
                environment.getProperty("DATABASE_URL")
        );

        if (rawUrl == null || rawUrl.isBlank()) {
            return;
        }

        Map<String, Object> properties = new HashMap<>();
        properties.put("spring.datasource.driver-class-name", "org.postgresql.Driver");

        if (rawUrl.startsWith("jdbc:")) {
            properties.put("spring.datasource.url", rawUrl);
            environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, properties));
            return;
        }

        if (!rawUrl.startsWith("postgres://") && !rawUrl.startsWith("postgresql://")) {
            return;
        }

        URI uri = URI.create(rawUrl.replaceFirst("^postgres://", "postgresql://"));
        String userInfo = uri.getUserInfo();
        String username = firstNonBlank(
                environment.getProperty("SPRING_DATASOURCE_USERNAME"),
                environment.getProperty("DATABASE_USERNAME")
        );
        String password = firstNonBlank(
                environment.getProperty("SPRING_DATASOURCE_PASSWORD"),
                environment.getProperty("DATABASE_PASSWORD")
        );

        if (userInfo != null && !userInfo.isBlank()) {
            String[] parts = userInfo.split(":", 2);
            if (username == null) {
                username = decode(parts[0]);
            }
            if (password == null && parts.length > 1) {
                password = decode(parts[1]);
            }
        }

        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        String database = uri.getPath() == null ? "" : uri.getPath().replaceFirst("^/", "");
        String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + port + "/" + database;
        jdbcUrl += uri.getQuery() == null || uri.getQuery().isBlank()
                ? "?sslmode=require"
                : "?" + uri.getQuery();

        properties.put("spring.datasource.url", jdbcUrl);
        if (username != null) {
            properties.put("spring.datasource.username", username);
        }
        if (password != null) {
            properties.put("spring.datasource.password", password);
        }

        environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, properties));
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
