package no.noroff.lagalt.controllers;

import no.noroff.lagalt.dtos.ProjectGetDTO;
import no.noroff.lagalt.mappers.ProjectMapper;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        Collection<ProjectGetDTO> projects = projectMapper.projectToProjectDTO(projectService.findAll());
        if (projects.size()>0){
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        ProjectGetDTO project = projectMapper.projectToProjectDTO(projectService.findById(id));
        if (project != null){
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
