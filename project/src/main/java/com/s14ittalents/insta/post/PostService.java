package com.s14ittalents.insta.post;

import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    public Post likePost(long id, HttpSession session) {
        if (!(session.getAttribute("id") == null || session.getAttribute("id") == ""
                || session.getAttribute("id").equals(0)) && session.getAttribute("logged").equals(true)) {
            Optional<Post> post = postRepository.findById(id);
            
            if (post.isPresent()) {
                post.get().getLikes().add(modelMapper
                        .map(userRepository.findById((long) session.getAttribute("id")), User.class));
                postRepository.save(post.get());
                return post.get();
            } else {
                throw new DataNotFoundException("Post not found");
            }
        } else {
            throw new NoAuthException("You have to login to like a post");
        }
    }
}
