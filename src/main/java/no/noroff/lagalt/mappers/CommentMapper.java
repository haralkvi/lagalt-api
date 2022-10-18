package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.details.CommentDetails;
import no.noroff.lagalt.dtos.get.CommentGetDTO;
import no.noroff.lagalt.dtos.post.CommentPostDTO;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.ProjectService;
import no.noroff.lagalt.services.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "project", source = "project.id")
    public abstract CommentGetDTO commentToCommentDTO(Comment comment);

    @Mapping(target = "user", source = "user", qualifiedByName = "idToUser")
    @Mapping(target = "project", source = "project", qualifiedByName = "idToProject")
    public abstract Comment commentPostDTOtoComment(CommentPostDTO commentPostDTO);

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

    @Named("idToProject")
    Project mapToProject(int id) {
        return projectService.findById(id);
    }

    @Named("idToUser")
    User mapToUser(String id) {
        return userService.findById(id);
    }


    abstract Comment commentDetailsToComment(CommentDetails commentDetails);

    abstract CommentDetails commentToCommentDetails(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract Comment updateCommentFromCommentDetails(CommentDetails commentDetails, @MappingTarget Comment comment);
}
