package no.noroff.lagalt.mappers;

import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ApplicationMapper {

    @Mapping(target = "user", source = "user", qualifiedByName = "userToId" )
    @Mapping(target = "project", source = "project", qualifiedByName = "projectToId")
    public abstract ApplicationGetDTO applicationToApplicationDTO(Application application);

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
        return value.getId();
    }

    @Named("projectToId")
    Integer mapToInt(Project value){
        return value.getId();
    }

}
