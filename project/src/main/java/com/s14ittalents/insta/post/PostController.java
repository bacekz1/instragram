package com.s14ittalents.insta.post;

import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.ExceptionController;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.NoAuthException;
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
        return post.orElseThrow(() -> new DataNotFoundException(Constant.DATA_NOT_FOUND));
    }
    
    /*@PostMapping("post//{id:[0-9]+}/like")
    Post likePost(@PathVariable long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            post.get().setLikes(post.get().getLikes() + 1);
            postRepository.save(post.get());
            return post.get();
        } else {
            throw new DataNotFoundException(Constant.DATA_NOT_FOUND);
        }
        
        --userLogin for controller abstract class
        getLoggedUserId()(HttpSession session) {
            if (session.isNew()) {
                throw new NoAuthException("You have to login");
                --in login set logged true and write the id of user
            }
            if((!session.getAttribute("logged").equals(true)) || session.getAttribute("logged") == null
            || session.getAttribute(REMOTE_IP).equals(ip)) {
                throw new NoAuthException("You have to login");
            }
            return (int) session.getAttribute("USER_ID");\
            
            --if logged get true from check, if not return false
            if new session is set - its from another client/ file with requests/ can make APIHttp  or Postman
        }
     */
}
