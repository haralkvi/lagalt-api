package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.ProjectGetDTO;
import no.noroff.lagalt.dtos.ProjectPostDTO;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    @Autowired
    UserService userService;

    @Mapping(target = "owner", source = "owner.id")
    @Mapping(target = "members", source = "members", qualifiedByName = "usersToIds")
    @Mapping(target = "userViews", source = "userViews", qualifiedByName = "usersToIds")
    @Mapping(target = "comments", source = "comments", qualifiedByName = "commentsToIds")
    @Mapping(target = "applications", source = "applications", qualifiedByName = "applicationsToIds")
    public abstract ProjectGetDTO projectToProjectDTO(Project project);

    @Mapping(target = "owner", source = "owner", qualifiedByName =  "idToOwner")
    public abstract Project projectPostDTOtoProject(ProjectPostDTO projectPostDTO);

    public Collection<ProjectGetDTO> projectToProjectDTO(Collection<Project> projects) {
        if (projects == null) {
            return null;
        }
        Collection<ProjectGetDTO> list = new ArrayList<>(projects.size());
        for (Project project : projects) {
            list.add(projectToProjectDTO(project));
        }
        return list;
    }

    @Named("usersToIds")
    Set<String> mapToString(Set<User> users) {
        if (users == null)
            return null;
        return users.stream()
                .map(User::getId).collect(Collectors.toSet());
    }

    @Named("commentsToIds")
    Set<Integer> mapCommentsToIds(Set<Comment> comments) {
        if (comments == null)
            return null;
        return comments.stream()
                .map(Comment::getId).collect(Collectors.toSet());
    }

    @Named("applicationsToIds")
    Set<Integer> mapApplicationsToIds(Set<Application> application) {
        if (application == null)
            return null;
        return application.stream()
                .map(Application::getApplication_id).collect(Collectors.toSet());
    }

    @Named("idToOwner")
    User mapToUser(String id){
        return userService.findById(id);
    }

}
