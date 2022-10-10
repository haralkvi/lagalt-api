package no.noroff.lagalt.controllers;

import no.noroff.lagalt.dtos.ApplicationGetDTO;
import no.noroff.lagalt.dtos.ApplicationPostDTO;
import no.noroff.lagalt.mappers.*;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
@RestController
@RequestMapping(path = "api/v1/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationMapper applicationMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        Collection<ApplicationGetDTO> applications = applicationMapper.applicationToApplicationDTO(applicationService.findAll());
        if (applications.size()>0){
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ApplicationGetDTO application = applicationMapper.applicationToApplicationDTO(applicationService.findById(id));
        if (application != null){
            return new ResponseEntity<>(application, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ApplicationPostDTO inputApplication) {
        Application application = applicationService.add(applicationMapper.applicationPostDTOtoApplication(inputApplication));
        if(application != null){
            URI location = URI.create("applications/ " + application.getApplication_id());
            return ResponseEntity.created(location).build();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    public ResponseEntity<Application> update(@RequestBody Application application, @PathVariable int id) {
        if (id != application.getApplication_id()) {
            return ResponseEntity.badRequest().build();
        }
        applicationService.update(application);
        return ResponseEntity.noContent().build();
    }


}
