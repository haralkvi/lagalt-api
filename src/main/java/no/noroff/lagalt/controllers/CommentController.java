package no.noroff.lagalt.controllers;

import no.noroff.lagalt.dtos.CommentGetDTO;
import no.noroff.lagalt.dtos.CommentPostDTO;
import no.noroff.lagalt.mappers.CommentMapper;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        Collection<CommentGetDTO> comments = commentMapper.commentToCommentDTO(commentService.findAll());
        if (comments.size()>0){
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        CommentGetDTO comment = commentMapper.commentToCommentDTO(commentService.findById(id));
        if (comment != null){
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CommentPostDTO inputComment) {
        Comment comment = commentService.add(commentMapper.commentPostDTOtoComment(inputComment));
        if(comment != null){
            URI location = URI.create("comments/" + comment.getId());
            return ResponseEntity.created(location).build();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Comment comment, @PathVariable int id) {
        if (id != comment.getId()) {
            return ResponseEntity.badRequest().build();
        }

        commentService.update(comment);

        return ResponseEntity.noContent().build();
    }

}
