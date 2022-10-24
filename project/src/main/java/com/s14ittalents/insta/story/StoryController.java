package com.s14ittalents.insta.story;

import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.post.*;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/story")
public class StoryController extends AbstractController {
    @Autowired
    StoryService storyService;

    @PostMapping
    PostWithoutOwnerDTO createStory(@ModelAttribute PostCreateDTO storyCreateDTO) {
        long userId = getLoggedUserId();
        return storyService.createStory(storyCreateDTO, userId);
    }
    @GetMapping("/{id:[0-9]+}")
    @ResponseBody
    PostWithoutOwnerDTO getStory(@PathVariable long id) {
        getLoggedUserId();
        return storyService.getStory(id);
    }

    @PutMapping("/{story:[0-9]+}")
    @ResponseBody
    PostWithoutOwnerDTO updateStory(@PathVariable long story, @RequestBody PostUpdateDTO storyUpdate) {
        long userId = getLoggedUserId();
        return storyService.updateStory(story, storyUpdate, userId);
    }

    @DeleteMapping("/{storyId:[0-9]+}")
    boolean deleteStory(@PathVariable long storyId) {
        long userId = getLoggedUserId();
        if (userId <= 0) {
            throw new NoAuthException("You are not logged in");
        }
        return storyService.deleteStory(storyId, userId);
    }
    
    @PostMapping("/{id:[0-9]+}/")
    int likePost(@PathVariable long id) {
        long userId = getLoggedUserId();
        return storyService.likeStory(id, userId);
    }
}
