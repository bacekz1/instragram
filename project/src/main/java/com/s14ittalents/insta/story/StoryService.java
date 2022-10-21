package com.s14ittalents.insta.story;

import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.PostRepository;
import com.s14ittalents.insta.post.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.USER_NOT_FOUND;

@Service
public class StoryService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    public PostWithoutOwnerDTO createStory(Post post, long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND));
        post.setOwner(user.get());
//        post.setCreatedTime(LocalDateTime.now());
//        post.setExpirationTime(LocalDateTime.now().plusDays(1));
        return modelMapper.map(postRepository.save(post), PostWithoutOwnerDTO.class);
    }

}
