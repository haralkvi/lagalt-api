package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.ProjectGetDTO;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    @Mapping(target = "owner", source = "owner", qualifiedByName = "ownerToString")
    public abstract ProjectGetDTO projectToProjectDTO(Project project);

    public Collection<ProjectGetDTO> projectToProjectDTO(Collection<Project> projects) {
        if (projects == null) {
            return null;
        }
        Collection<ProjectGetDTO> list = new ArrayList<ProjectGetDTO>(projects.size());
        for (Project project : projects) {
            list.add(projectToProjectDTO(project));
        }
        return list;
    }

    @Named("ownerToString")
    String mapToString(User value){
        return value.getName();
    }

}
