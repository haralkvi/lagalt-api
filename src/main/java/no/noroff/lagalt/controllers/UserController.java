package no.noroff.lagalt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.noroff.lagalt.dtos.get.ProjectGetDTO;
import no.noroff.lagalt.dtos.get.UserGetDTO;
import no.noroff.lagalt.dtos.post.UserPostDTO;
import no.noroff.lagalt.exceptions.ApiErrorResponse;
import no.noroff.lagalt.exceptions.EmailAlreadyExistsException;
import no.noroff.lagalt.exceptions.IdAlreadyExistsException;
import no.noroff.lagalt.exceptions.UserNotFoundException;
import no.noroff.lagalt.mappers.ProjectMapper;
import no.noroff.lagalt.mappers.UserMapper;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.dtos.put.UserPutDTO;
import no.noroff.lagalt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@CrossOrigin(
        origins = {
                "https://beste-lagalt.herokuapp.com/",
                "http://localhost:3000/"
        })
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
                    content ={ @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserGetDTO.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "No users found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
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
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserGetDTO.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Specified user not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        UserGetDTO user = userMapper.userToUserDTO(userService.findById(id));
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Fetches the currently logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user has been received",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserGetDTO.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Specified user not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("current")
    public ResponseEntity<?> getByJwt(@AuthenticationPrincipal Jwt jwt) {
        User userModel = userService.findById(jwt.getClaimAsString("sub"));

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
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectGetDTO.class)) }),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Specified user not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("recommendations")
    public ResponseEntity<?> getRecommendations(@AuthenticationPrincipal Jwt jwt) {
        User user = userService.findById(jwt.getClaimAsString("sub"));

        if (user != null) {
            List<ProjectGetDTO> recommendedProjects =
                    projectMapper.projectToProjectDTO(
                            userService.findRecommendations(user)
                    ).stream().toList();
            return new ResponseEntity<>(recommendedProjects, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }    @Operation(summary = "Get current user's recommended projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Recommended projects have successfully been fetched",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Specified user not found",
                    content = @Content)
    })
    @GetMapping("{id}/recommendations")
    public ResponseEntity<?> getUserRecommendations(@PathVariable String id) {
        User user = userService.findById(id);

        if (user != null) {
            List<ProjectGetDTO> recommendedProjects =
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
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserPostDTO inputUser) {
        // user's id has to be unique
        if (userService.existsById(inputUser.getId())) {
            throw new IdAlreadyExistsException(inputUser.getId());
        }

        // user's email has to be unique
        if (userService.existsByEmail(inputUser.getEmail())) {
            throw new EmailAlreadyExistsException(inputUser.getEmail());
        }

        User user  = userService.add(userMapper.userPostDTOtoUser(inputUser));

        if (user != null) {
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
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PostMapping("register")
    public ResponseEntity<?> addByJwt(@AuthenticationPrincipal Jwt jwt) {
        // user's id has to be unique
        if (userService.existsById(jwt.getClaimAsString("sub"))) {
            throw new IdAlreadyExistsException(jwt.getClaimAsString("sub"));
        }

        // user's email has to be unique
        if (userService.existsByEmail(jwt.getClaimAsString("email"))) {
            throw new EmailAlreadyExistsException(jwt.getClaimAsString("email"));
        }

        User user = userService.addById(jwt);
        URI uri = URI.create("api/v1/users/" + user.getId());
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Updates a specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The user has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "User not found with supplied ID",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody UserPutDTO inputUser, @PathVariable String id) {
        if (!id.equals(inputUser.getId())) {
            return ResponseEntity.badRequest().build();
        }

        userService.update(userMapper.userPutDTOtoUser(inputUser));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Updates a given user's set of skills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The skill set has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing changed",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Provided user ID does not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}/skillset")
    public ResponseEntity<?> updateSkillset(@RequestBody String[] skills, @PathVariable String id) {
        userService.updateSkillset(skills, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Adds a certain project (id) into a users click history")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The project has been added to the users click history",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing has been added",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Provided project ID does not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })

    @PutMapping("{id}/click-history")
    public ResponseEntity<?> addToClickHistory(@RequestBody int projectId, @PathVariable String id){
        userService.addToClickHistory(projectId, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Changes description of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The user description has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing changed",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Provided user ID does not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}/description")
    public ResponseEntity<?> changeDescription(@RequestBody String[] description, @PathVariable String id){
        if(description.length != 1)return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.changeDescription(description, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Updates currently logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The user has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("update")
    public ResponseEntity<?> updateByJwt(@RequestBody UserPostDTO userPostDTO, @AuthenticationPrincipal Jwt jwt) {
        String id = jwt.getClaimAsString("sub");
        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        userService.update(userMapper.userPostDTOtoUser(userPostDTO));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletes a specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The user has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The specified user does not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        if (!userService.existsById(id)) {
            throw new UserNotFoundException(id);
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
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteByJwt(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getClaimAsString("sub");

        if (uid == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteById(uid);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Toggle logged in user's hidden status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "User's hidden status has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The specified user does not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PatchMapping("hidden")
    public ResponseEntity<?> toggleHiddenStatus(@AuthenticationPrincipal Jwt jwt){
        String uid = jwt.getClaimAsString("sub");

        if (uid == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.changeHiddenStatus(uid);
        return ResponseEntity.noContent().build();
    }
}
