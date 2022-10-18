package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.get.UserGetDTO;
import no.noroff.lagalt.dtos.post.UserPostDTO;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.models.UserPutDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "projectsParticipated", source = "projectsParticipated", qualifiedByName = "projectsToIds")
    @Mapping(target = "projectsHistory", source = "projectsHistory", qualifiedByName = "projectsToIds")
    @Mapping(target = "projectsOwned", source = "projectsOwned", qualifiedByName = "projectsToIds")
    public abstract UserGetDTO userToUserDTO(User user);

    @Mapping(target = "hidden", constant = "false")
    public abstract User userPostDTOtoUser(UserPostDTO userPostDTO);

    @Mapping(target = "hidden", constant = "false")
    public abstract User userPutDTOtoUser(UserPutDTO userPutDTO);

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
        if (projects == null)
            return null;
        return projects.stream()
                .map(Project::getId).collect(Collectors.toSet());
    }

}
