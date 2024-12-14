package tenkacheva.work.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import reactor.core.publisher.Mono;
import tenkacheva.work.app.models.Role;
import tenkacheva.work.app.repositories.UserRepository;

public class UserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(user -> {
                    System.out.println(username); return User.builder()
                        .username(user.getUsername())
                        .password("{noop}" + user.getPassword())
                        .roles(user.getRoles().stream().map(Role::name).toArray(String[]::new))
                        .build();});
    }
}
