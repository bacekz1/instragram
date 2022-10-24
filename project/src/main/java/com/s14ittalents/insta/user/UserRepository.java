package com.s14ittalents.insta.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

        Optional<User> findByUsername(String username);
        Optional<User> findByEmail(String email);
        Optional<User> findByEmailAndPasswordOrUsernameAndPassword(String email, String password
                , String username, String password1);

        
}
