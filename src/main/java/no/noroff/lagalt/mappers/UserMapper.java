package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.UserGetDTO;
import no.noroff.lagalt.dtos.UserPostDTO;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "projectsParticipated", source = "projectsParticipated",qualifiedByName = "projectsToIds" )
    @Mapping(target = "projectsHistory", source = "projectsHistory",qualifiedByName = "projectsToIds" )
    @Mapping(target = "projectsOwned", source = "projectsOwned", qualifiedByName = "projectsToIds")
    public abstract UserGetDTO userToUserDTO(User user);

    public abstract User userPostDTOtoUser(UserPostDTO userPostDTO);

    public Collection<UserGetDTO> userToUserDTO(Collection<User> users) {
        if (users == null) {
            return null;
        }
        Collection<UserGetDTO> list = new ArrayList<>(users.size());
        for (User user : users) {
            list.add(userToUserDTO(user));
        }
        return list;
    }

    @Named("projectsToIds")
    Set<Integer> mapToInteger(Set<Project> projects) {
        if(projects == null)
            return null;
        return projects.stream()
                .map(Project::getId).collect(Collectors.toSet());
    }

}
