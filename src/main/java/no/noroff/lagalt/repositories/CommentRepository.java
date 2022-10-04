package no.noroff.lagalt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import no.noroff.lagalt.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
