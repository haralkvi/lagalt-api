package no.noroff.lagalt.controllers;

import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody User inputUser) {
        User user = userService.add(inputUser);
        URI location = URI.create("users/ " + inputUser.getId());

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody User user, @PathVariable int id) {
        if (id != user.getId()) {
            return ResponseEntity.badRequest().build();
        }

        userService.update(user);

        return ResponseEntity.noContent().build();
    }

}
