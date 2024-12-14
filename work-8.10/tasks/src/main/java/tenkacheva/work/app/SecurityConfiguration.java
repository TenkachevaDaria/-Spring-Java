package tenkacheva.work.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import tenkacheva.work.app.repositories.UserRepository;
import tenkacheva.work.app.services.UserDetailsService;

import static tenkacheva.work.app.models.Role.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/user/**")
                        .hasAnyRole(MANAGER.name(), USER.name())
                        .pathMatchers(HttpMethod.GET, "/task", "/task/*")
                        .hasAnyRole(MANAGER.name(), USER.name())
                        .pathMatchers(HttpMethod.PUT, "/task/*/*")
                        .hasAnyRole(MANAGER.name(), USER.name())
                        .pathMatchers(HttpMethod.POST, "/task")
                        .hasRole(MANAGER.name())
                        .pathMatchers(HttpMethod.PUT, "/task/*")
                        .hasRole(MANAGER.name())
                        .pathMatchers(HttpMethod.DELETE, "/task/*")
                        .hasRole(MANAGER.name()))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsService(userRepository);
    }
}
