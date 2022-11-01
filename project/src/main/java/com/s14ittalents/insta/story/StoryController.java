package com.s14ittalents.insta.story;

import com.s14ittalents.insta.content.dto.ContentIdDTO;
import com.s14ittalents.insta.post.dto.PostCreateDTO;
import com.s14ittalents.insta.story.dto.StoryWithoutOwnerDTO;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stories")
public class StoryController extends AbstractController {
    @Autowired
    StoryService storyService;

    @PostMapping
    StoryWithoutOwnerDTO createStory(@ModelAttribute PostCreateDTO storyCreateDTO) {
        long userId = getLoggedUserId();
        return storyService.createStory(storyCreateDTO, userId);
    }

    @GetMapping
    @ResponseBody
    StoryWithoutOwnerDTO getStory() {
        long userId = getLoggedUserId();
        return storyService.getStory(userId);
    }

    @PutMapping
    void updateStory(@RequestBody ContentIdDTO contentIdDTO) {
        long userId = getLoggedUserId();
        storyService.updateStory(contentIdDTO, userId);
    }

    @DeleteMapping()
    boolean deleteStory() {
        long userId = getLoggedUserId();
        return storyService.deleteStory(userId);
    }

    @PostMapping("/{storyId:[0-9]+}")
    int likeStory(@PathVariable long storyId) {
        long userId = getLoggedUserId();
        return storyService.likeStory(storyId, userId);
    }
}
