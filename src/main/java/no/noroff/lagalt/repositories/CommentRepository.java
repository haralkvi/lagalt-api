package no.noroff.lagalt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import no.noroff.lagalt.models.Comment;

import javax.transaction.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE comment SET text=?1 WHERE comment.comment_id=?2", nativeQuery = true)
    void updateTextById(String text, int id);
}
