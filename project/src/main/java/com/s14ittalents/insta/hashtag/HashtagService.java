package com.s14ittalents.insta.hashtag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashtagService {
    @Autowired
    HashtagRepository hashtagRepository;

    Hashtag createHashtag(Hashtag hashtag) {
        return hashtagRepository.save(hashtag);
    }
}
