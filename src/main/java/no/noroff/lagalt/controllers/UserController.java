package no.noroff.lagalt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.noroff.lagalt.dtos.UserGetDTO;
import no.noroff.lagalt.dtos.UserPostDTO;
import no.noroff.lagalt.mappers.UserMapper;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @Operation(summary = "Gets all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All users received",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        Collection<UserGetDTO> users = userMapper.userToUserDTO(userService.findAll());
        if (users.size()>0){
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Gets a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user has been received",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Specified user not found",
                    content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        UserGetDTO user = userMapper.userToUserDTO(userService.findById(id));
        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Fetches the currently logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user has been received",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Specified user not found",
                    content = @Content)
    })
    @GetMapping("current")
    public ResponseEntity getByJwt(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(
                userService.findByUid(
                        jwt.getClaimAsString("sub")
                )
        );
    }

    @Operation(summary = "Creates a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing created",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserPostDTO inputUser) {
        User user  = userService.add(userMapper.userPostDTOtoUser(inputUser));
        if(user != null){
            URI location = URI.create("users/" + user.getId());
            return ResponseEntity.created(location).build();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Creates a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "User successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing created",
                    content = @Content)
    })
    @PostMapping("register")
    public ResponseEntity addByJwt(@AuthenticationPrincipal Jwt jwt) {
        User user = userService.addByUid(jwt);
        URI uri = URI.create("api/v1/users/" + user.getUid());
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Updates a specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = @Content)
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable int id) {
        if (id != user.getId()) {
            return ResponseEntity.badRequest().build();
        }
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Updates currently logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = @Content)
    })
    @PutMapping("update")
    public ResponseEntity<?> updateByJwt(@RequestBody User user, @AuthenticationPrincipal Jwt jwt) {
        String uid = jwt.getClaimAsString("sub");

        if (uid != user.getUid()) {
            return ResponseEntity.badRequest().build();
        }

        userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletes a specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The user has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The specified user does not exist",
                    content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletes currently logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The user has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The specified user does not exist",
                    content = @Content)
    })
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteByJwt(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getClaimAsString("sub");

        if (uid == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteByUid(uid);
        return ResponseEntity.noContent().build();
    }


}
