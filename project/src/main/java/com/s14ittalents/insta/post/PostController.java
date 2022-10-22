package com.s14ittalents.insta.post;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    Post createPost(@RequestBody Post post, HttpSession httpSession) {
        long userId = (long) httpSession.getAttribute("id");
        post.setCreatedTime(LocalDateTime.now());
        post.setExpirationTime(LocalDateTime.now().plusDays(1));
        postService.createPost(post, userId);
        return post;
    }

    @GetMapping("/{id:[0-9]+}")
    @ResponseBody
    PostWithoutOwnerDTO getPost(@PathVariable long id) {

        return postService.getPost(id);
    }

    @PutMapping("/{id:[0-9]+}")
    @ResponseBody
    PostWithoutOwnerDTO updatePost(@PathVariable long id, @RequestBody PostUpdateDTO postUpdate) {
//        int userId = httpSession.getAttribute()
        //TODO
        return postService.updatePost(id, postUpdate);
    }

    @PostMapping("{id:[0-9]+}/like")
    PostWithoutOwnerDTO likePost(@PathVariable long id, HttpSession session, HttpServletRequest request) {
        return modelMapper.map(postService.likePost(id, session, request), PostWithoutOwnerDTO.class);
    @PostMapping("/{id:[0-9]+}/")
    int likePost(@PathVariable long id, HttpSession session, HttpServletRequest request) {
        long userId = getLoggedUserId(session,request);
        if(userId<=0){
            throw new NoAuthException("You are not logged in");
        }else {
            return postService.likePost(id, userId);
        }
    }
    /*
            if new session is set - its from another client/ file with requests/ can make APIHttp  or Postman
     */
}
