package com.s14ittalents.insta.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndDeletedIsFalse(long id);
    List<Comment> findByPostId(long id);
    
    List<Comment> getAllByOwnerId(long id);
}
