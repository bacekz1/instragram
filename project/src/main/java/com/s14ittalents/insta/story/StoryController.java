package com.s14ittalents.insta.story;

import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.post.dto.PostCreateDTO;
import com.s14ittalents.insta.post.dto.PostUpdateDTO;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
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
//    @GetMapping()
//    @ResponseBody
//    PostWithoutOwnerDTO getStory() {
//       long userId = getLoggedUserId();
//        return storyService.getStory(userId);
//    }


    @DeleteMapping("/{storyId:[0-9]+}")
    boolean deleteStory(@PathVariable long storyId) {
        long userId = getLoggedUserId();
        if (userId <= 0) {
            throw new NoAuthException("You are not logged in");
        }
        return storyService.deleteStory(storyId, userId);
    }
    
    @PostMapping("/{postId:[0-9]+}/")
    int likePost(@PathVariable long postId) {
        long userId = getLoggedUserId();
        return storyService.likeStory(postId, userId);
    }
}
