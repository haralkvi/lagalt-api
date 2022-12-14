package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.details.ApplicationDetails;
import no.noroff.lagalt.dtos.details.CommentDetails;
import no.noroff.lagalt.dtos.get.ProjectGetDTO;
import no.noroff.lagalt.dtos.post.ProjectPostDTO;
import no.noroff.lagalt.dtos.put.ProjectPutDTO;
import no.noroff.lagalt.dtos.details.UserDetails;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsMapper userDetailsMapper;

    @Autowired
    CommentDetailsMapper commentDetailsMapper;

    @Autowired
    private ApplicationDetailsMapper applicationDetailsMapper;

    @Mapping(target = "owner", source = "owner", qualifiedByName = "userToUserDetails")
    @Mapping(target = "members", source = "members", qualifiedByName = "usersToUserDetails")
    @Mapping(target = "userViews", source = "userViews", qualifiedByName = "usersToUserDetails")
    @Mapping(target = "comments", source = "comments", qualifiedByName = "commentsToCommentDetails")
    @Mapping(target = "applications", source = "applications", qualifiedByName = "applicationsToApplicationDetails")
    public abstract ProjectGetDTO projectToProjectDTO(Project project);

    @Mapping(target = "owner", source = "owner", qualifiedByName = "idToOwner")
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

    public List<ProjectGetDTO> projectToProjectDTO(List<Project> projects) {
        if (projects == null) {
            return null;
        }
        List<ProjectGetDTO> list = new ArrayList<>(projects.size());
        for (Project project : projects) {
            list.add(projectToProjectDTO(project));
        }
        return list;
    }

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "userViews", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "applications", ignore = true)
    public abstract Project projectPutDTOToProject(ProjectPutDTO projectPutDTO);

    @Named("commentsToCommentDetails")
    Set<CommentDetails> mapCommentsToDetails(Set<Comment> comments) {
        if (comments == null)
            return null;
        return comments.stream()
                .map(commentDetailsMapper::commentToCommentDetails).collect(Collectors.toSet());
    }

    @Named("applicationsToApplicationDetails")
    Set<ApplicationDetails> mapApplicationsToDetails(Set<Application> applications) {
        if (applications == null)
            return null;
        return applications.stream()
                .map(applicationDetailsMapper::applicationToApplicationDetails).collect(Collectors.toSet());
    }

    @Named("userToUserDetails")
    UserDetails mapUserToDetails(User user) {
        return userDetailsMapper.userToUserDetails(user);
    }

    @Named("usersToUserDetails")
    Set<UserDetails> mapUsersToDetails(Set<User> users) {
        if (users == null)
            return null;
        return users.stream()
                .map(this::mapUserToDetails).collect(Collectors.toSet());
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
    User mapToUser(String id) {
        return userService.findById(id);
    }
}
