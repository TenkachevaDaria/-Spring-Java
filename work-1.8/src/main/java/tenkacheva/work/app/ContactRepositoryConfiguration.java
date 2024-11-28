package tenkacheva.work.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
public class ContactRepositoryConfiguration {
    @Bean
    @Profile("production")
    public ContactRepository getOnProductionProfile(
            @Value("${app.contacts.path}") String path) throws IOException {
        return new ContactFileRepositoryImpl(path);
    }

    @Bean
    @Profile("init")
    public ContactRepository getOnInitProfile(
            @Value("${app.contacts.path}") String to,
            @Value("${app.resources.contacts.path}") String from) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (var stream = classLoader.getResourceAsStream(from)) {
            return new ContactFileRepositoryImpl(stream, to);
        }
    }
}
