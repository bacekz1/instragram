package com.s14ittalents.insta.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Hashtag findByTagName(String text);
}
