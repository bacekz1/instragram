package com.s14ittalents.insta.story;

import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.content.dto.ContentIdDTO;
import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.NoAuthException;
import com.s14ittalents.insta.location.Location;
import com.s14ittalents.insta.post.*;
import com.s14ittalents.insta.post.dto.PostCreateDTO;
import com.s14ittalents.insta.story.dto.StoryWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.scheduling.annotation.Scheduled;
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
    private static final int HOUR = 1000 * 60 * 60;

    @Transactional
    public StoryWithoutOwnerDTO createStory(PostCreateDTO storyCreateDTO, long userId) {
        User user = getUserById(userId);
        checkPermission(user);
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
            if (locationName != null && !locationName.isEmpty() && !locationName.isBlank()) {
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
            return modelMapper.map(createdStory, StoryWithoutOwnerDTO.class);

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
            return modelMapper.map(story.get(), StoryWithoutOwnerDTO.class);
        }
    }

    public StoryWithoutOwnerDTO getStory(long userId) {
        Post story = findStoryByUserId(userId);
        checkPermission(story.getOwner(), story);
        return modelMapper.map(story, StoryWithoutOwnerDTO.class);
    }

    public void updateStory(ContentIdDTO contentIdDTO, long userId) {
        Post story = findStoryByUserId(userId);
        checkPermission(story.getOwner(), story);
        Content content = contentRepository
                .findById(contentIdDTO.getId()).orElseThrow(() -> new DataNotFoundException(CONTENT_NOT_FOUND));
        if (!story.getContents().contains(content)) {
            throw new NoAuthException(PERMISSION_DENIED);
        }
        contentRepository.delete(content);
    }

    public boolean deleteStory(long userId) {
        Post story = findStoryByUserId(userId);
        checkPermission(story.getOwner(), story);
        story.setDeleted(true);
        story.setCaption(REPLACE_IN_DELETED);
        postRepository.save(story);
        return true;
    }

    public int likeStory(long storyId, long userId) {
        Post story = findStoryById(storyId);
        User user = getUserById(userId);
        checkPermission(user, story);
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

    @Scheduled(fixedDelay = HOUR)
    @Transactional
    public void deleteExpiredStories(){
        postRepository.deleteExpiredStories(REPLACE_IN_DELETED);
    }

}
