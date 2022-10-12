package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.ApplicationGetDTO;
import no.noroff.lagalt.dtos.ApplicationPostDTO;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.ProjectService;
import no.noroff.lagalt.services.UserService;
import no.noroff.lagalt.services.UserServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ApplicationMapper {

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Mapping(target = "user", source = "user", qualifiedByName = "userToId" )
    @Mapping(target = "project", source = "project", qualifiedByName = "projectToId")
    public abstract ApplicationGetDTO applicationToApplicationDTO(Application application);

    @Mapping(target = "user", source = "user", qualifiedByName = "idToUser" )
    @Mapping(target = "project", source = "project", qualifiedByName= "idToProject")
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


    @Named("userToId")
    Integer mapToInt(User value){
        if (value == null) {
            return null;
        }

        return value.getId();
    }

    @Named("idToUser")
    User mapToUser(int id){
        return userService.findById(id);
    }

    @Named("projectToId")
    Integer mapToInt(Project value){
        return value.getId();
    }

    @Named("idToProject")
    Project mapToProject(int id){
        return projectService.findById(id);
    }

}
