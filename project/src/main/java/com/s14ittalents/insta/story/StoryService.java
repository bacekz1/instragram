package com.s14ittalents.insta.story;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.content.ContentIdDTO;
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
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.*;

@Service
public class StoryService extends AbstractService {
    private static final int MAX_SIZE = 50;

    @Transactional
    public PostWithoutOwnerDTO createStory(PostCreateDTO storyCreateDTO, long userId) {
        Optional<Post> story = postRepository.findStoryByUserId(userId);
        if (story.isEmpty()) {
            Post createStory = new Post();
            createStory.setCaption(storyCreateDTO.getCaption());
            createStory.setOwner(getUserById(userId));
            createStory.setCreatedTime(LocalDateTime.now());
            createStory.setExpirationTime(LocalDateTime.now().plusDays(1));
            addHashtags(createStory);
            addPersonTags(createStory);
            Location location = null;
            String locationName = storyCreateDTO.getLocation();
            if (locationName != null) {
                location = locationRepository.findByName(locationName)
                        .orElseGet(() -> locationRepository.save(new Location(locationName)));
            }
            createStory.setLocationId(location);
            Post createdStory = postRepository.save(createStory);
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

        } else {
            List<MultipartFile> files = storyCreateDTO.getContents();
            if (files != null) {
                if (files.size() > MAX_ALLOWED_FILES_TO_UPLOAD) {
                    throw new BadRequestException(YOU_CAN_ONLY_CHOOSE_10_OR_FEWER_FILES);
                }
                List<Content> contents = uploadFiles(files, userId, story.get(), MAX_SIZE);
                List<Content> createdContents = contentRepository.saveAll(contents);
                story.get().getContents().addAll(createdContents);
            }
            return modelMapper.map(story.get(), PostWithoutOwnerDTO.class);
        }
    }

    public PostWithoutOwnerDTO getStory(long userId) {
        Post story = findStoryByUserId(userId);
        return modelMapper.map(story, PostWithoutOwnerDTO.class);
    }

//TODO
    public void updatePost(ContentIdDTO contentIdDTO, long userId) {
        Post story = findStoryByUserId(userId);
        Content content = contentRepository
                .findById(contentIdDTO.getId()).orElseThrow(() -> new DataNotFoundException(CONTENT_NOT_FOUND));
        story.getContents().remove(content);
        postRepository.save(story);
    }

    public boolean deleteStory(long userId) {
        Post story = findStoryByUserId(userId);
        checkPermission(userId, story);
        List<Comment> comments = story.getComments().stream().toList();
        comments.forEach(comment -> comment.setDeleted(true));
        commentRepository.saveAll(comments);
        story.setDeleted(true);
        story.setCaption("deleted at" + LocalDateTime.now());
        postRepository.save(story);
        return true;
    }

    public int likeStory(long storyId, long userId) {
        Post story = findStoryById(storyId);
        User user = getUserById(userId);
        if (user.getLikedPosts().contains(story)) {
            user.getLikedPosts().remove(story);
            story.getLikes().remove(user);
        } else {
            user.getLikedPosts().add(story);
            story.getLikes().add(user);
        }
        userRepository.save(user);
        return story.getLikes().size();
    }

}
