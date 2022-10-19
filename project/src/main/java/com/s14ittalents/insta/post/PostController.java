package com.s14ittalents.insta.post;

import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.ExceptionController;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.user.UserRepository;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    Post createPost(@RequestBody Post post) {
        postRepository.save(post);
        return post;
    }

    @GetMapping("/{id:[0-9]+}")
    @ResponseBody
    Post getPost(@PathVariable long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new DataNotFoundException(Constant.DATA_NOT_FOUND));
    }
    
    //return post withNoOwner
    @GetMapping("/{id:[0-9]+}/noOwnerPosts")
    @ResponseBody
    PostWithoutOwnerDTO getPost2(@PathVariable long id) {
        try {
            PostWithoutOwnerDTO post = modelMapper.map(postRepository.findById(id), PostWithoutOwnerDTO.class);
            return post;
        } catch (Exception e) {
            throw new DataNotFoundException("Post not found");
        }
    }
    @PostMapping("post/{id:[0-9]+}/like")
    PostWithoutOwnerDTO likePost(@PathVariable long id, HttpSession session) {
        return modelMapper.map(postService.likePost(id, session),PostWithoutOwnerDTO.class);
    }
    /*
        
        --userLogin for controller abstract class
        
            
            --if logged get true from check, if not return false
            if new session is set - its from another client/ file with requests/ can make APIHttp  or Postman
        }
     */
}
