package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.details.ApplicationDetails;
import no.noroff.lagalt.dtos.details.ProjectDetails;
import no.noroff.lagalt.dtos.details.UserDetails;
import no.noroff.lagalt.dtos.get.ApplicationGetDTO;
import no.noroff.lagalt.dtos.post.ApplicationPostDTO;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.ProjectService;
import no.noroff.lagalt.services.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ApplicationMapper {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    private UserDetailsMapper userDetailsMapper;

    @Autowired
    private ProjectDetailsMapper projectDetailsMapper;

    @Mapping(target = "user", source = "user", qualifiedByName = "userToUserDetails")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectToProjectDetails")
    public abstract ApplicationGetDTO applicationToApplicationDTO(Application application);

    @Mapping(target = "user", source = "user", qualifiedByName = "idToUser")
    @Mapping(target = "project", source = "project", qualifiedByName = "idToProject")
    public abstract Application applicationPostDTOtoApplication(ApplicationPostDTO applicationPostDTO);

    public Collection<ApplicationGetDTO> applicationToApplicationDTO(Collection<Application> applications) {
        if (applications == null) {
            return null;
        }
        Collection<ApplicationGetDTO> list = new ArrayList<ApplicationGetDTO>(applications.size());
        for (Application application : applications) {
            list.add(applicationToApplicationDTO(application));
        }
        return list;
    }

    @Named("userToUserDetails")
    UserDetails mapUserToDetails(User user) {
        return userDetailsMapper.userToUserDetails(user);
    }

    @Named("projectToProjectDetails")
    ProjectDetails mapProjectToDetails(Project project) {
        return projectDetailsMapper.projectToProjectDetails(project);
    }

    @Named("idToUser")
    User mapToUser(String id) {
        return userService.findById(id);
    }

    @Named("idToProject")
    Project mapToProject(int id) {
        return projectService.findById(id);
    }
}
