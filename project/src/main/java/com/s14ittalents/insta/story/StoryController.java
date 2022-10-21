package com.s14ittalents.insta.story;

import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.PostWithoutOwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/story")
public class StoryController {
    @Autowired
    StoryService storyService;

    @PostMapping
    PostWithoutOwnerDTO createPost(@RequestBody Post post, HttpSession httpSession) {
        long userId = (long) httpSession.getAttribute("id");
        return storyService.createStory(post, userId);
    }
}
