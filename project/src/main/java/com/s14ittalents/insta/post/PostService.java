package com.s14ittalents.insta.post;

import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.hashtag.Hashtag;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import com.s14ittalents.insta.util.AbstractService;
import com.s14ittalents.insta.util.Helper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.*;

@Service
public class PostService extends AbstractService {

    public Post createPost(Post post, long userId) {
        post.setOwner(getUserById(userId));
        List<Hashtag> hashtags = Helper.findHashTags(post.getCaption()).stream().map(h -> new Hashtag(h)).toList();
        List<User> users = Helper.findPersonTags(post.getCaption()).stream()
                .map(tag -> userRepository.findByUsername(tag.replaceAll("@","")))
                .filter(Optional::isPresent).map(Optional::get).toList();
        System.out.println(users.size());
        post.getHashtags().addAll(hashtags);
        post.getPersonTags().addAll(users);
        return postRepository.save(post);
    }

    public PostWithoutOwnerDTO getPost(long id) {
        Post post = postRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new DataNotFoundException(POST_NOT_FOUND));
        System.out.println(post.isDeleted());
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }

    public Post likePost(long id, HttpSession session, HttpServletRequest req) {
        if (getLoggedUserId(session, req) == -1) {
            Optional<Post> post = postRepository.findByIdAndDeletedIsFalse(id);
            post.orElseThrow(() -> new DataNotFoundException(POST_NOT_FOUND));

            post.get().getLikes().add(userRepository.findById((long) session.getAttribute("id"))
                    .orElseThrow(() -> new DataNotFoundException(USER_NOT_FOUND)));
            getUserById((long) session.getAttribute("id")).getLikedPosts().add(post.get());
            post.get().getLikes().forEach(a -> System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
            postRepository.save(post.get());
            userRepository.save(getUserById((long) session.getAttribute("id")));
            post.get().getLikes().forEach(a -> System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
            return post.get();

        } else {
            throw new NoAuthException("You have to login to like a post");
        }
    }
}
