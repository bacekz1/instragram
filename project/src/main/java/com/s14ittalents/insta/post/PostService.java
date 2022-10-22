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
        Post post = findPost(id);
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }

    public PostWithoutOwnerDTO updatePost(long id, PostUpdateDTO postUpdate) {
        Post post = findPost(id);
        post.setCaption(postUpdate.getCaption());
        post.getHashtags().clear();
        post.getPersonTags().clear();
        addHashtags(post);
        addPersonTags(post);
        postRepository.save(post);
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }

    public int likePost(long id, long userId) {
            Optional<Post> post = postRepository.findByIdAndDeletedIsFalse(id);
            if (post.isEmpty()){
                throw new DataNotFoundException(POST_NOT_FOUND);
            }
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()){
                throw new DataNotFoundException(USER_NOT_FOUND);
            }

            if(post.get().getLikes().contains(user.get())) {
                post.get().getLikes().remove(getUserById(userId));
            } else {
                post.get().getLikes().add(getUserById(userId));
            }
            post.get().getLikes().forEach(a ->
                    System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
            userRepository.save(getUserById(userId));
            post.get().getLikes().forEach(a ->
                    System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
            return post.get().getLikes().size();
        }
    }
