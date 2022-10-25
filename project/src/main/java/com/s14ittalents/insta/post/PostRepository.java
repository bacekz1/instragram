package com.s14ittalents.insta.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndDeletedIsFalseAndExpirationTimeIsNull(long id);

    Optional<Post> findByIdAndDeletedIsFalseAndExpirationTimeNotNullAndCreatedTimeIsNotNull(long id);
    Page<Post> findByOwnerIdAndDeletedIsFalseAndExpirationTimeIsNullOrderByCreatedTimeDesc(long id, Pageable pageable);

}
