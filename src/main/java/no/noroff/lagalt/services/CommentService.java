package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Comment;

public interface CommentService extends CrudService<Comment, Integer> {
    void updateText(String text, int id);
}
