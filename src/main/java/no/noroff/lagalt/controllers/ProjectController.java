package no.noroff.lagalt.controllers;

import no.noroff.lagalt.models.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/projects")
public class ProjectController {

    @GetMapping
    public ResponseEntity<Collection<Project>> getAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getById(@PathVariable int id) {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Project project) {
        Project proj = projectService.add(project);
        URI location = URI.create("projects/ " + project.getId());
        
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody Project project, @PathVariable int id) {
        if (id != project.getId()) {
            return ResponseEntity.badRequest().build();
        }

        projectService.update(project);

        return ResponseEntity.noContent().build();
    }

}
