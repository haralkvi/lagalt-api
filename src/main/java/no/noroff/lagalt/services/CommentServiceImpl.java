package no.noroff.lagalt.services;

import no.noroff.lagalt.exceptions.CommentNotFoundException;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    public Collection<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment add(Comment entity) {
        return commentRepository.save(entity);
    }

    @Override
    public Comment update(Comment entity) {
        return commentRepository.save(entity);
    }

    @Override
    public void deleteById(Integer integer) {
        commentRepository.deleteById(integer);
    }

    @Override
    public void delete(Comment entity) {
        commentRepository.delete(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return commentRepository.existsById(id);
    }

    @Override
    public void updateText(String text, int id) {
        commentRepository.updateTextById(text, id);
    }
}
