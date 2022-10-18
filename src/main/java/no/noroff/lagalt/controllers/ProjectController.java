package no.noroff.lagalt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.noroff.lagalt.dtos.get.ProjectGetDTO;
import no.noroff.lagalt.dtos.post.ProjectPostDTO;
import no.noroff.lagalt.dtos.put.ProjectPutDTO;
import no.noroff.lagalt.mappers.ProjectMapper;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.ProjectService;
import no.noroff.lagalt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserService userService;

    @Operation(summary = "Gets all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All projects received",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        Collection<ProjectGetDTO> projects = projectMapper.projectToProjectDTO(projectService.findAll());
        if (projects.size()>0){
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Gets a specific project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The project has been received",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Specified project not found",
                    content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ProjectGetDTO project = projectMapper.projectToProjectDTO(projectService.findById(id));
        if (project != null){
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Creates a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Project successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing created",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ProjectPostDTO projectInput) {
        if (!userService.existsById(projectInput.getOwner())) {
            return ResponseEntity.notFound().build();
        }

        Project project = projectService.add(projectMapper.projectPostDTOtoProject(projectInput));
        userService.addMembers(new String[]{project.getOwner().getId()}, project.getId());

        if (project != null) {
            URI location = URI.create("projects/" + project.getId());
            return ResponseEntity.created(location).build();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Updates a specified project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The project has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = @Content)
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody ProjectPutDTO project, @PathVariable int id) {
        if (id != project.getId()) {
            return ResponseEntity.badRequest().build();
        }
        projectService.update(projectMapper.projectPutDTOToProject(project));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletes a specified project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The project has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The specified project does not exist",
                    content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        if (!projectService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{projectId}/add-member")
    public ResponseEntity<?> addMember(@AuthenticationPrincipal Jwt jwt, @PathVariable int projectId) {
        String uId = jwt.getClaimAsString("sub");

        if (uId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.addMember(uId, projectId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/members")
    public ResponseEntity<?> addMember(@RequestBody String[] members, @PathVariable int id) {

        for (String uId : members) {
            if (!userService.existsById(uId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        userService.addMembers(members, id);
        return ResponseEntity.noContent().build();
    }

}
