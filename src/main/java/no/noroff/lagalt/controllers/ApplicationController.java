package no.noroff.lagalt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.noroff.lagalt.dtos.get.ApplicationGetDTO;
import no.noroff.lagalt.dtos.post.ApplicationPostDTO;
import no.noroff.lagalt.exceptions.ApiErrorResponse;
import no.noroff.lagalt.exceptions.ApplicationNotFoundException;
import no.noroff.lagalt.mappers.ApplicationMapper;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
@CrossOrigin(
        origins = {
                "https://sheltered-inlet-34111.herokuapp.com",
                "http://localhost:3000/"
        })
@RestController
@RequestMapping(path = "api/v1/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Operation(summary = "Gets all applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All applications received",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Application.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "No applications found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        Collection<ApplicationGetDTO> applications = applicationMapper.applicationToApplicationDTO(applicationService.findAll());
        if (applications.size() > 0) {
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Gets a specific application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The application has been received",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Application.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Specified application not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ApplicationGetDTO application = applicationMapper.applicationToApplicationDTO(applicationService.findById(id));
        if (application != null) {
            return new ResponseEntity<>(application, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Creates a application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Application successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing created",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Provided user or project not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ApplicationPostDTO inputApplication) {
        Application application = applicationService.add(applicationMapper.applicationPostDTOtoApplication(inputApplication));
        if (application != null) {
            URI location = URI.create("applications/" + application.getApplication_id());
            return ResponseEntity.created(location).build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates a specified application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The application has been updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Malformed body, nothing received",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}")
    public ResponseEntity<Application> update(@RequestBody Application application, @PathVariable int id) {
        if (id != application.getApplication_id()) {
            return ResponseEntity.badRequest().build();
        }

        if (!applicationService.existsById(id)) {
            throw new ApplicationNotFoundException(id);
        }

        applicationService.update(application);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletes a specified application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The application has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The specified application does not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (!applicationService.existsById(id)) {
            throw new ApplicationNotFoundException(id);
        }

        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
