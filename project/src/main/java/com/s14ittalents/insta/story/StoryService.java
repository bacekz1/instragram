package com.s14ittalents.insta.story;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.location.Location;
import com.s14ittalents.insta.post.*;
import com.s14ittalents.insta.post.dto.PostCreateDTO;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.s14ittalents.insta.exception.Constant.MAX_ALLOWED_FILES_TO_UPLOAD;
import static com.s14ittalents.insta.exception.Constant.YOU_CAN_ONLY_CHOOSE_10_OR_FEWER_FILES;

@Service
public class StoryService extends AbstractService {
    private static final int MAX_SIZE = 50;

    @Transactional
    public PostWithoutOwnerDTO createStory(PostCreateDTO storyCreateDTO, long userId) {
        Post story = new Post();
        story.setCaption(storyCreateDTO.getCaption());
        story.setOwner(getUserById(userId));
        story.setCreatedTime(LocalDateTime.now());
        story.setExpirationTime(LocalDateTime.now().plusDays(1));
        addHashtags(story);
        addPersonTags(story);
        Location location = null;
        String locationName = storyCreateDTO.getLocation();
        if (locationName != null) {
            location = locationRepository.findByName(locationName)
                    .orElseGet(() -> locationRepository.save(new Location(locationName)));
        }
        story.setLocationId(location);
        Post createdStory = postRepository.save(story);
        List<MultipartFile> files = storyCreateDTO.getContents();
        if (files != null) {
            if (files.size() > MAX_ALLOWED_FILES_TO_UPLOAD) {
                throw new BadRequestException(YOU_CAN_ONLY_CHOOSE_10_OR_FEWER_FILES);
            }
            List<Content> contents = uploadFiles(files, userId, createdStory, MAX_SIZE);
            List<Content> createdContents = contentRepository.saveAll(contents);
            createdStory.setContents(createdContents);
        }
        return modelMapper.map(createdStory, PostWithoutOwnerDTO.class);
    }

//    public PostWithoutOwnerDTO getStory(long userId) {
//         Post story = postRepository.findByOwnerAndDeletedIsFalseAndExpirationTimeNotNullAndCreatedTimeIsNotNull(getUserById(userId))
//                 .orElseThrow(() -> new DataNotFoundException("test"));
//        return modelMapper.map(story, PostWithoutOwnerDTO.class);
//    }

    @Transactional
    public boolean deleteStory(long storyId, long userId) {
        Post story = findStory(storyId);
        checkPermission(userId, story);
        List<Comment> comments = commentRepository.findByPostId(storyId).stream().toList();
        comments.forEach(comment -> comment.setDeleted(true));
        commentRepository.saveAll(comments);
        story.setDeleted(true);
        story.setCaption("deleted at" + LocalDateTime.now());
        postRepository.save(story);
        return true;
    }
    
    public int likeStory(long postId, long userId) {
        Post post = findStory(postId);
        User user = getUserById(userId);
        if (user.getLikedPosts().contains(post)) {
            user.getLikedPosts().remove(post);
            post.getLikes().remove(user);
        } else {
            user.getLikedPosts().add(post);
            post.getLikes().add(user);
        }
        userRepository.save(user);
        return post.getLikes().size();
    }
}
