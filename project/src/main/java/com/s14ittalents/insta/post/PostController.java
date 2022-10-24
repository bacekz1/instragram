package com.s14ittalents.insta.post;

import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController extends AbstractController {
    @Autowired
    PostService postService;

    @PostMapping
    PostWithoutOwnerDTO createPost(@ModelAttribute PostCreateDTO postCreateDTO) {
        long userId = getLoggedUserId();
        return postService.createPost(postCreateDTO, userId);
    }

    @GetMapping("/{id:[0-9]+}")
    @ResponseBody
    PostWithoutOwnerDTO getPost(@PathVariable long id) {
        getLoggedUserId();
        return postService.getPost(id);
    }

    @PutMapping("/{postId:[0-9]+}")
    @ResponseBody
    PostWithoutOwnerDTO updatePost(@PathVariable long postId, @RequestBody PostUpdateDTO postUpdate) {
        long userId = getLoggedUserId();
        return postService.updatePost(postId, postUpdate, userId);
    }

    @DeleteMapping("/{postId:[0-9]+}")
    boolean deletePost(@PathVariable long postId) {
        long userId = getLoggedUserId();
        if (userId <= 0) {
            throw new NoAuthException("You are not logged in");
        }
        return postService.deletePost(postId, userId);
    }

    @PostMapping("/{id:[0-9]+}/")
    int likePost(@PathVariable long id) {
        long userId = getLoggedUserId();
            return postService.likePost(id, userId);
    }
    /*
            if new session is set - its from another client/ file with requests/ can make APIHttp  or Postman
     */
}
