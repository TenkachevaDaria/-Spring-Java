package tenkacheva.work.app.restControllers;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tenkacheva.work.app.dtos.UserDTO;
import tenkacheva.work.app.dtos.RequestUserDTO;
import tenkacheva.work.app.services.UserService;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Publisher<UserDTO> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Publisher<UserDTO> getById(@PathVariable String id) {
        return userService.get(id);
    }

    @PostMapping
    public Publisher<ResponseEntity<UserDTO>> create(RequestUserDTO user) {
        return userService
                .create(user.username(), user.email())
                .map(u -> ResponseEntity.created(URI.create("/user/" + u.id()))
                        .build());
    }

    @DeleteMapping("/{id}")
    public Publisher<UserDTO> deleteById(@PathVariable String id) {
        return userService.delete(id);
    }

    @PutMapping("/{id}")
    public Publisher<UserDTO> updateById(
            @PathVariable String id,
            RequestUserDTO user
    ) {
        return userService.update(id, user.username(), user.email());
    }
}
