package no.noroff.lagalt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.noroff.lagalt.dtos.get.ProjectGetDTO;
import no.noroff.lagalt.dtos.post.ProjectPostDTO;
import no.noroff.lagalt.dtos.put.ProjectPutDTO;
import no.noroff.lagalt.exceptions.ProjectNotFoundException;
import no.noroff.lagalt.exceptions.UserNotFoundException;
import no.noroff.lagalt.mappers.ProjectMapper;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.services.ProjectService;
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
@RequestMapping(path = "api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectMapper projectMapper;


    @Operation(summary = "Gets all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All projects received",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Projects not found",
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
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "Creates a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Project successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing created",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No such user ID",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ProjectPostDTO projectInput) {
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
            @ApiResponse(responseCode = "204",
                    description = "The project has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Project not found with supplied ID",
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
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add currently logged in user as a member to a project", deprecated = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "User has been added as member of project",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The project or user with provided IDs do not exist",
                    content = @Content)
    })
    @PutMapping("{projectId}/add-member")
    public ResponseEntity<?> addMember(@AuthenticationPrincipal Jwt jwt, @PathVariable int projectId) {
        String uId = jwt.getClaimAsString("sub");

        if (uId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.addMember(uId, projectId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Add member to project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "User succesfully added as member to project",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Project or user not found",
                    content = @Content)
    })
    @PostMapping("{id}/members")
    public ResponseEntity<?> addMember(@RequestBody String[] members, @PathVariable int id) {

        for (String uId : members) {
            if (!userService.existsById(uId)) {
                throw new UserNotFoundException(uId);
            }
        }

        userService.addMembers(members, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove member from project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Member has been removed",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Project or user has not been found",
                    content = @Content)
    })
    @DeleteMapping("{id}/members")
    public ResponseEntity<?> removeMember(@RequestBody String[] members, @PathVariable int id) {

        for (String uId : members) {
            if (!userService.existsById(uId)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        userService.removeMembers(members, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update project's list of tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Project's tags list has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Project not found",
                    content = @Content)
    })
    @PutMapping("{id}/tags")
    public ResponseEntity<?> addTags(@RequestBody String[] tags, @PathVariable int id) {
        if (!projectService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        projectService.addTags(tags, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update project's list of needed skills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Project's list of needed skills has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Project not found",
                    content = @Content)
    })
    @PutMapping("{id}/needed-skills")
    public ResponseEntity<?> updatedSkills(@RequestBody String[] skills, @PathVariable int id) {
        if (!projectService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        projectService.addSkills(skills, id);
        return ResponseEntity.noContent().build();
    }
}
