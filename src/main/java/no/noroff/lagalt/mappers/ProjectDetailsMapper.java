package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.ProjectDetails;
import no.noroff.lagalt.models.Project;
import org.mapstruct.Mapping;


public abstract class ProjectDetailsMapper {

    abstract Project projectDetailsToProject(ProjectDetails projectDetails);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "summary", source = "summary")
    @Mapping(target = "link", source = "link")
    abstract ProjectDetails projectToProjectDetails(Project project);
}
