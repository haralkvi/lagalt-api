package no.noroff.lagalt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.noroff.lagalt.dtos.ProjectGetDTO;
import no.noroff.lagalt.dtos.UserGetDTO;
import no.noroff.lagalt.dtos.UserPostDTO;
import no.noroff.lagalt.mappers.ProjectMapper;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProjectMapper projectMapper;

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
    public ResponseEntity<?> getByJwt(@AuthenticationPrincipal Jwt jwt) {
        User userModel = userService.findByUid(jwt.getClaimAsString("sub"));

        if (userModel != null) {
            UserGetDTO userGetDTO = userMapper.userToUserDTO(userModel);
            return new ResponseEntity<>(userGetDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get current user's recommended projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Recommended projects have successfully been fetched",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Specified user not found",
                    content = @Content)
    })
    public ResponseEntity<?> getRecommendations(@AuthenticationPrincipal Jwt jwt) {
        User user = userService.findByUid(jwt.getClaimAsString("sub"));

        if (user != null) {
            Collection<ProjectGetDTO> recommendedProjects =
                    projectMapper.projectToProjectDTO(
                            userService.findRecommendations(user)
                    );
            return new ResponseEntity<>(recommendedProjects, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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
    public ResponseEntity<?> addByJwt(@AuthenticationPrincipal Jwt jwt) {
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
    public ResponseEntity<?> update(@RequestBody UserPostDTO inputUser, @PathVariable int id) {
        User user = userMapper.userPostDTOtoUser(inputUser);
        user.setId(id);
        User userPost = userService.update(user);
        if(userPost != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Adds skills to a certain users skillset")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The skills has been added to the users skillset",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing changed",
                    content = @Content)
    })
    @PutMapping("skillset/{id}")
    public ResponseEntity<?> addToSkillset(@RequestBody String[] skillsetPostDTO, @PathVariable int id){
        if(skillsetPostDTO.length > 0)new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.addSkillset(skillsetPostDTO,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Adds a certain project (id) into a users click history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The project has been added to the users click history",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing has been added",
                    content = @Content)
    })
    @PutMapping("history/{id}")
    public ResponseEntity<?> addToClickHistory(@RequestBody Integer[] projectId, @PathVariable int id){
        if(projectId.length > 0)new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.addToClickHistory(projectId,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Changes description of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user description has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing changed",
                    content = @Content)
    })
    @PutMapping("description/{id}")
    public ResponseEntity<?> changeDescription(@RequestBody String[] description, @PathVariable Integer id){
        if(description.length != 1)new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.changeDescription(description, id);
        return new ResponseEntity<>(HttpStatus.OK);
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

        if (uid.equals(user.getUid())) {
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

    @PutMapping("hidden")
    public ResponseEntity<?> toggleHiddenStatus(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getClaimAsString("sub");

        if (uid == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.changeHiddenStatus(uid);
        return ResponseEntity.noContent().build();
    }
}
