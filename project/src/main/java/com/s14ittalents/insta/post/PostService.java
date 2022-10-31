package com.s14ittalents.insta.post;

import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.location.Location;
import com.s14ittalents.insta.post.dto.PostCreateDTO;
import com.s14ittalents.insta.post.dto.PostUpdateDTO;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
        if (files != null) {
            if (files.size() > MAX_ALLOWED_FILES_TO_UPLOAD) {
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
        checkPermission(getUserById(userId), post);
        post.setCaption(postUpdate.getCaption());
        post.getHashtags().clear();
        post.getPersonTags().clear();
        addHashtags(post);
        addPersonTags(post);
        postRepository.save(post);
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }

    @Transactional
    public void deletePostComments(long postId, long userId) {
        Post post = findPost(postId);
        checkPermission(getUserById(userId), post);
        post.setDeleted(true);
        post.setCaption(REPLACE_IN_DELETED);
        deletePostComments(post);
        post.getLikes().clear();
        postRepository.save(post);
    }
    
    @Transactional
    public void deletePostCommentsWhenDeletingUser(Post post) {
        post.setDeleted(true);
        post.setCaption(REPLACE_IN_DELETED);
        deletePostComments(post);
        postRepository.save(post);
    }

    public int likePost(long postId, long userId) {
        Post post = findPost(postId);
        User user = getUserById(userId);
        if (user.getLikedPosts().contains(post)) {
            user.getLikedPosts().remove(post);
            post.getLikes().remove(user);
        } else {
            user.getLikedPosts().add(post);
            post.getLikes().add(user);
        }
        postRepository.save(post);
        return post.getLikes().size();
    }

    private void deletePostComments(Post post) {
        if (post.getComments() != null) {
            for (int i = 0; i < post.getComments().size(); i++) {
                post.getComments().get(i).setDeleted(true);
                post.getComments().get(i).setComment("deleted at" + LocalDateTime.now());
                post.getComments().forEach(c-> c.getLikes().clear());
            }
        }
    }

    public Page<PostWithoutOwnerDTO> getMyPosts(long userId, int page) {
        Pageable pages = PageRequest.of(page, 12);
        List<PostWithoutOwnerDTO> list = postRepository
                .findByOwnerIdAndDeletedIsFalseAndExpirationTimeIsNullOrderByCreatedTimeDesc(userId, pages)
                .stream()
                .map(p -> modelMapper.map(p, PostWithoutOwnerDTO.class)).toList();
        return new PageImpl<>(list);

    }
}
