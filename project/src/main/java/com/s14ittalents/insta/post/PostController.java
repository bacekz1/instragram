package com.s14ittalents.insta.post;

import com.s14ittalents.insta.post.dto.PostCreateDTO;
import com.s14ittalents.insta.post.dto.PostUpdateDTO;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
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

    @GetMapping("/my/{page}")
    @ResponseBody
    Page<PostWithoutOwnerDTO> getMyPosts(@PathVariable int page) {
        long userId = getLoggedUserId();
        return postService.getMyPosts(userId, page);
    }

    @PutMapping("/{postId:[0-9]+}")
    @ResponseBody
    PostUpdateDTO updatePost(@PathVariable long postId, @RequestBody PostUpdateDTO postUpdate) {
        long userId = getLoggedUserId();
        return postService.updatePost(postId, postUpdate, userId);
    }

    @DeleteMapping("/{postId:[0-9]+}")
    void deletePost(@PathVariable long postId) {
        long userId = getLoggedUserId();
        postService.deletePostComments(postId, userId);
    }

    //TODO
    @PostMapping("/{postId:[0-9]+}/")
    int likePost(@PathVariable long postId) {
        long userId = getLoggedUserId();
        return postService.likePost(postId, userId);
    }
}
