package no.noroff.lagalt.controllers;

import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<Collection<Comment>> getAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Comment> getById(@PathVariable int id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Comment inputComment) {
        Comment comment = commentService.add(inputComment);
        URI location = URI.create("comments/ " + inputComment.getId());

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity update(@RequestBody Comment comment, @PathVariable int id) {
        if (id != comment.getId()) {
            return ResponseEntity.badRequest().build();
        }

        commentService.update(comment);

        return ResponseEntity.noContent().build();
    }

}
