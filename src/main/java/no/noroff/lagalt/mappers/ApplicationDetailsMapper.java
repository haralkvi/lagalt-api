package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.details.ApplicationDetails;
import no.noroff.lagalt.dtos.details.CommentDetails;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Comment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ApplicationDetailsMapper {

    @Mapping(target = "user", source = "user.id")
    abstract ApplicationDetails applicationToApplicationDetails(Application application);
}
