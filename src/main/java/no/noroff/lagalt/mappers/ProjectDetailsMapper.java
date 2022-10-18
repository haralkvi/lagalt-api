package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.details.ProjectDetails;
import no.noroff.lagalt.models.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class ProjectDetailsMapper {

    public abstract Project projectDetailsToProject(ProjectDetails projectDetails);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "summary", source = "summary")
    @Mapping(target = "link", source = "link")
    public abstract ProjectDetails projectToProjectDetails(Project project);
}
