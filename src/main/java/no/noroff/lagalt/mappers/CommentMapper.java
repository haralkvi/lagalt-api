package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.ApplicationGetDTO;
import no.noroff.lagalt.dtos.CommentGetDTO;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Mapping(target = "user", source = "user", qualifiedByName = "userToId" )
    @Mapping(target = "project", source = "project", qualifiedByName = "projectToId")
    public abstract CommentGetDTO commentToCommentDTO(Comment comment);

    public Collection<CommentGetDTO> commentToCommentDTO(Collection<Comment> comments) {
        if (comments == null) {
            return null;
        }
        Collection<CommentGetDTO> list = new ArrayList<CommentGetDTO>(comments.size());
        for (Comment comment : comments) {
            list.add(commentToCommentDTO(comment));
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
