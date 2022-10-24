package com.s14ittalents.insta.post;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.location.Location;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.*;

@Service
public class PostService extends AbstractService {
    private static final int MAX_SIZE = 5;

    @Transactional
    public PostWithoutOwnerDTO createPost(PostCreateDTO postCreateDTO, long userId) {
        Post post = new Post();
        post.setCaption(postCreateDTO.getCaption());
        post.setOwner(getUserById(userId));
        post.setCreatedTime(LocalDateTime.now());
        addHashtags(post);
        addPersonTags(post);
        Location location = null;
        String locationName = postCreateDTO.getLocation();
        if (locationName != null) {
            location = locationRepository.findByName(locationName)
                    .orElseGet(() -> locationRepository.save(new Location(locationName)));
        }
        post.setLocationId(location);
        Post createdPost = postRepository.save(post);
        List<MultipartFile> files = postCreateDTO.getContents();
        if (postCreateDTO.getContents() != null) {
            if (postCreateDTO.getContents().size() > MAX_ALLOWED_FILES_TO_UPLOAD) {
                throw new BadRequestException(YOU_CAN_ONLY_CHOOSE_10_OR_FEWER_FILES);
            }
            List<Content> contents = uploadFiles(files, userId, createdPost, MAX_SIZE);
            List<Content> createdContents = contentRepository.saveAll(contents);
            createdPost.setContents(createdContents);
        }
        return modelMapper.map(createdPost, PostWithoutOwnerDTO.class);
    }

    public PostWithoutOwnerDTO getPost(long id) {
        Post post = findPost(id);
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }

    public PostWithoutOwnerDTO updatePost(long posId, PostUpdateDTO postUpdate, long userId) {
        Post post = findPost(posId);
        checkPermission(userId, post);
        post.setCaption(postUpdate.getCaption());
        post.getHashtags().clear();
        post.getPersonTags().clear();
        addHashtags(post);
        addPersonTags(post);
        postRepository.save(post);
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }


    @Transactional
    public boolean deletePost(long postId, long userId) {
        Post post = findPost(postId);
        checkPermission(userId, post);
        List<Comment> comments = commentRepository.findByPostId(postId).stream().toList();
        comments.forEach(comment -> comment.setDeleted(true));
        commentRepository.saveAll(comments);
        post.setDeleted(true);
        post.setCaption("deleted at" + LocalDateTime.now());
        postRepository.save(post);
        return true;
    }

    public int likePost(long id, long userId) {
        Optional<Post> post = postRepository.findByIdAndDeletedIsFalseAndExpirationTimeIsNull(id);
        if (post.isEmpty()) {
            throw new DataNotFoundException(POST_NOT_FOUND);
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new DataNotFoundException(USER_NOT_FOUND);
        }

        if (post.get().getLikes().contains(user.get())) {
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
