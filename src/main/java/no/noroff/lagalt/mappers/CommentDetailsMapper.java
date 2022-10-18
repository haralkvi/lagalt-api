package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.details.CommentDetails;
import no.noroff.lagalt.models.Comment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class CommentDetailsMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "time", source = "time")
    @Mapping(target = "username", source = "user.name")
    abstract CommentDetails commentToCommentDetails(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract Comment updateCommentFromCommentDetails(CommentDetails commentDetails, @MappingTarget Comment comment);

}
