package com.s14ittalents.insta.post;

import com.s14ittalents.insta.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndDeletedIsFalseAndExpirationTimeIsNull(long id);
    Optional<Post> findByIdAndDeletedIsFalseAndExpirationTimeNotNullAndCreatedTimeIsNotNull(long id);
    Page<Post> findByOwnerIdAndDeletedIsFalseAndExpirationTimeIsNullOrderByCreatedTimeDesc(long id, Pageable pageable);

    @Query(
            value = "SELECT * FROM USERS u WHERE u.status = 1",
            nativeQuery = true)
    Collection<User> findAllActiveUsersNative();

}
