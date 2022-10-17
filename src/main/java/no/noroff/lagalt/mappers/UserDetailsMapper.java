package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.UserDetails;
import no.noroff.lagalt.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public abstract class UserDetailsMapper {

    abstract User userDetailsToUser(UserDetails userDetails);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "description", source = "description")
    abstract UserDetails userToUserDetails(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract User updateUserFromUserDetails(UserDetails userDetails, @MappingTarget User user);

}
