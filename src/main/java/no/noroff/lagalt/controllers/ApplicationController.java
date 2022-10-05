package no.noroff.lagalt.controllers;

import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<Collection<Application>> getAll() {
        return ResponseEntity.ok(applicationService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Application> getById(@PathVariable int id) {
        return ResponseEntity.ok(applicationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Application> add(@RequestBody Application inputApplication) {
        Application application = applicationService.add(inputApplication);
        URI location = URI.create("applications/ " + inputApplication.getApplication_id());

        return ResponseEntity.created(location).build();
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
