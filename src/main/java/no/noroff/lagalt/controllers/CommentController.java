package no.noroff.lagalt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.noroff.lagalt.dtos.get.CommentGetDTO;
import no.noroff.lagalt.dtos.post.CommentPostDTO;
import no.noroff.lagalt.exceptions.ApiErrorResponse;
import no.noroff.lagalt.exceptions.CommentNotFoundException;
import no.noroff.lagalt.mappers.CommentMapper;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@CrossOrigin(
        origins = {
                "https://beste-lagalt.herokuapp.com/",
                "http://localhost:3000/"
        })
@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @Operation(summary = "Gets all comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All comments received",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommentGetDTO.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "No comments found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
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
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommentGetDTO.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Specified comment not found",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        CommentGetDTO comment = commentMapper.commentToCommentDTO(commentService.findById(id));
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Creates a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Comment successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing created",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Provided user or project IDs do not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<?> add(@RequestBody CommentPostDTO inputComment) {
        Comment comment = commentService.add(commentMapper.commentPostDTOtoComment(inputComment));
        URI location = URI.create("comments/" + comment.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Deletes a specified comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "The comment has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "The specified comment does not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        if (!commentService.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Updates a comment's text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Comment successfully created",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed body, nothing created",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) }),
            @ApiResponse(responseCode = "404",
                    description = "Provided user or project IDs do not exist",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponse.class)) })
    })
    @PutMapping("{id}/text")
    public ResponseEntity<?> editComment(@RequestBody String text, @PathVariable int id) {
        if (!commentService.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        commentService.updateText(text, id);
        return ResponseEntity.noContent().build();
    }

}
