package tenkacheva.work.app.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tenkacheva.work.app.repositories.PersonRepository;

public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalPerson = personRepository.findByName(username);
        if (optionalPerson.isEmpty()) {
            throw new UsernameNotFoundException("User cannot be found: " + username);
        }

        var person = optionalPerson.get();
        return User.builder()
                .username(username)
                .password("{noop}" + person.getPassword())
                .roles(person.getRole().name())
                .build();
    }
}

