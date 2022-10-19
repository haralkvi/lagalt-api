package no.noroff.lagalt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.noroff.lagalt.dtos.get.CommentGetDTO;
import no.noroff.lagalt.dtos.post.CommentPostDTO;
import no.noroff.lagalt.mappers.CommentMapper;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    @Operation(summary = "Gets all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All comments received",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing received",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        Collection<CommentGetDTO> comments = commentMapper.commentToCommentDTO(commentService.findAll());
        if (comments.size()>0){
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Gets a specific comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The comment has been received",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Specified comment not found",
                    content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        CommentGetDTO comment = commentMapper.commentToCommentDTO(commentService.findById(id));
        if (comment != null){
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Creates a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Comment successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing created",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> add(@RequestBody CommentPostDTO inputComment) {
        // comment's user has to correspond to user existing in database
        if (!userService.existsById(inputComment.getUser()) ||
        // comment's project has to correspond to project existing in database
        !projectService.existsById(inputComment.getProject())) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Comment comment = commentService.add(commentMapper.commentPostDTOtoComment(inputComment));

        if (comment != null) {
            URI location = URI.create("comments/" + comment.getId());
            return ResponseEntity.created(location).build();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Deletes a specified comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The comment has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The specified comment does not exist",
                    content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/text")
    public ResponseEntity editComment(@RequestBody String text, @PathVariable int id) {
        if (!commentService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        commentService.updateText(text, id);
        return ResponseEntity.noContent().build();
    }

}
