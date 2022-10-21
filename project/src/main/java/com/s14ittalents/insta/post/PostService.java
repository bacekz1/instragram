package com.s14ittalents.insta.post;

import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.Constant;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserRepository;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.*;

@Service
public class PostService {


    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    public Post createPost(Post post, long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND));
        post.setOwner(user.get());
        return postRepository.save(post);
    }

    public PostWithoutOwnerDTO getPost(long id) {
        Post post = postRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new DataNotFoundException(POST_NOT_FOUND));
        System.out.println(post.isDeleted());
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }

    public Post likePost(long id, HttpSession session) {
        if (!(session.getAttribute("id") == null || session.getAttribute("id") == ""
                || session.getAttribute("id").equals(0)) && session.getAttribute("logged").equals(true)) {
            Optional<Post> post = postRepository.findByIdAndDeletedIsFalse(id);
            post.orElseThrow(() -> new DataNotFoundException(POST_NOT_FOUND));

            post.get().getLikes().add(userRepository.findById((long) session.getAttribute("id"))
                    .orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND)));
            post.get().getLikes().forEach(a -> System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
            postRepository.save(post.get());
            post.get().getLikes().forEach(a -> System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
            return post.get();

        } else {
            throw new NoAuthException("You have to login to like a post");
        }
    }
}
