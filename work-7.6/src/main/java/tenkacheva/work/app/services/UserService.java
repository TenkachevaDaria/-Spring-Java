package tenkacheva.work.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tenkacheva.work.app.dtos.UserDTO;
import tenkacheva.work.app.mappers.UserMapper;
import tenkacheva.work.app.models.User;
import tenkacheva.work.app.repositories.UserRepository;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Flux<UserDTO> getAll() {
        return userRepository
                .findAll()
                .map(userMapper::userToUserDTO);
    }

    public Mono<UserDTO> get(String id) {
        return userRepository.findById(id).map(userMapper::userToUserDTO);
    }

    public Mono<UserDTO> create(String username, String email) {
        return userRepository
                .save(new User(null, username, email))
                .map(userMapper::userToUserDTO);
    }

    public Mono<UserDTO> update(String id, String username, String email) {
        return userRepository
                .findById(id)
                .map(user -> new User(user.getId(), username, email))
                .flatMap(userRepository::save)
                .map(userMapper::userToUserDTO);
    }

    public Mono<UserDTO> delete(String id) {
        return userRepository
                .findById(id)
                .flatMap(user -> userRepository.deleteById(user.getId()).thenReturn(user))
                .map(userMapper::userToUserDTO);
    }
}
