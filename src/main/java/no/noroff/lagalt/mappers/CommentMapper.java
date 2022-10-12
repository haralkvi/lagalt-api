package no.noroff.lagalt.mappers;

import no.noroff.lagalt.dtos.ApplicationGetDTO;
import no.noroff.lagalt.dtos.ApplicationPostDTO;
import no.noroff.lagalt.dtos.CommentGetDTO;
import no.noroff.lagalt.dtos.CommentPostDTO;
import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.services.ProjectService;
import no.noroff.lagalt.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Mapping(target = "user", source = "user", qualifiedByName = "userToId" )
    @Mapping(target = "project", source = "project", qualifiedByName = "projectToId")
    public abstract CommentGetDTO commentToCommentDTO(Comment comment);

    @Mapping(target = "user", source = "user", qualifiedByName = "idToUser" )
    @Mapping(target = "project", source = "project", qualifiedByName= "idToProject")
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



    @Named("userToId")
    Integer mapToInt(User value){
        if (value == null) {
            return null;
        }

        return value.getId();
    }

    @Named("projectToId")
    Integer mapToInt(Project value){
        return value.getId();
    }

    @Named("idToProject")
    Project mapToProject(int id){
        return projectService.findById(id);
    }

    @Named("idToUser")
    User mapToUser(int id){
        return userService.findById(id);
    }


}
