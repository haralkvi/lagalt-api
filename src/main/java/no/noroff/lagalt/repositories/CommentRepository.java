package no.noroff.lagalt.repositories;

import org.springframework.stereotype.Repository;
import no.noroff.lagalt.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
