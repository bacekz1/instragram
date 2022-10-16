package com.s14ittalents.insta.post;

import com.s14ittalents.insta.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new DataNotFoundException("Data not found"));
    }
}
