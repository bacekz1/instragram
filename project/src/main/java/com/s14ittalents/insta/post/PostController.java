package com.s14ittalents.insta.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @PostMapping
    Post createPost(@RequestBody Post post) {
        postRepository.save(post);
        return post;
    }

    @GetMapping("/{id:[0-9]+}")
    @ResponseBody
    Post getPost(@PathVariable long id) {
        Post post = postRepository.findById(id);
        if (post == null){
            throw new RuntimeException();
        }
        System.out.println(post);
        return post;
    }
}
