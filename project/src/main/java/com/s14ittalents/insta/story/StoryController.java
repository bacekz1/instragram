package com.s14ittalents.insta.story;

import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.PostService;
import com.s14ittalents.insta.post.PostUpdateDTO;
import com.s14ittalents.insta.post.PostWithoutOwnerDTO;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/story")
public class StoryController extends AbstractController {
    @Autowired
    StoryService storyService;
    @Autowired
    PostService postService;

    @PostMapping
    PostWithoutOwnerDTO createStory(@RequestBody Post post) {
        long userId = getLoggedUserId();
        return storyService.createStory(post, userId);
    }
    @GetMapping("/{id:[0-9]+}")
    @ResponseBody
    PostWithoutOwnerDTO getStory(@PathVariable long id) {
        getLoggedUserId();
        return storyService.getStory(id);
    }

    @PutMapping("/{postId:[0-9]+}")
    @ResponseBody
    PostWithoutOwnerDTO updateStory(@PathVariable long postId, @RequestBody PostUpdateDTO postUpdate) {
        long userId = getLoggedUserId();
        return storyService.updateStory(postId, postUpdate, userId);
    }

    @DeleteMapping("/{postId:[0-9]+}")
    boolean deleteStory(@PathVariable long postId) {
        long userId = getLoggedUserId();
        if (userId <= 0) {
            throw new NoAuthException("You are not logged in");
        }
        return storyService.deleteStory(postId, userId);
    }
}
