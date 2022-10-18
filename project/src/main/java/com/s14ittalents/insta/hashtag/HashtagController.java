package com.s14ittalents.insta.hashtag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hashtag")
public class HashtagController {
    @Autowired
    HashtagService hashtagService;

    @PostMapping()
    Hashtag createHashtag(@RequestBody Hashtag hashtag) {
        return hashtagService.createHashtag(hashtag);
    }

}
