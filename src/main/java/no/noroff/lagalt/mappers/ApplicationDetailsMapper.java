package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.details.ApplicationDetails;
import no.noroff.lagalt.dtos.details.CommentDetails;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Comment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ApplicationDetailsMapper {

    @Mapping(target = "application_id", source = "application_id")
    @Mapping(target = "time", source = "time")
    abstract ApplicationDetails applicationToApplicationDetails(Application application);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract Application updateApplicationFromApplicationDetails(ApplicationDetails applicationDetails, @MappingTarget Application application);

}
