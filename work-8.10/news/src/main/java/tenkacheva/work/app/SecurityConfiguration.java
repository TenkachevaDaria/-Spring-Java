package tenkacheva.work.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import tenkacheva.work.app.repositories.PersonRepository;
import tenkacheva.work.app.services.CustomUserDetailsService;

import static tenkacheva.work.app.models.Role.*;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpRequestsCustomizer())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PersonRepository personRepository) {
        return new CustomUserDetailsService(personRepository);
    }

    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> httpRequestsCustomizer() {
        return authorize -> authorize
                .requestMatchers("/api/person/all")
                .hasRole(ADMIN.name())
                .requestMatchers("/api/category/*")
                .hasAnyRole(ADMIN.name(), MODERATOR.name())
                .requestMatchers("/api/news/*")
                .permitAll()
                .anyRequest()
                .hasAnyRole(ADMIN.name(), MODERATOR.name(), USER.name());
    }
}
