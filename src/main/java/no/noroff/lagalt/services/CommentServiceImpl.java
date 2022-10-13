package no.noroff.lagalt.services;

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
    public Comment findById(Integer integer) {
        Optional<Comment> opt = commentRepository.findById(integer);
        return opt.orElse(null);
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
}
