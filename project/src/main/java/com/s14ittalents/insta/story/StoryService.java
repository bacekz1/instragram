package com.s14ittalents.insta.story;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.location.Location;
import com.s14ittalents.insta.post.*;
import com.s14ittalents.insta.post.dto.PostCreateDTO;
import com.s14ittalents.insta.post.dto.PostUpdateDTO;
import com.s14ittalents.insta.post.dto.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserWithoutPostsDTO;
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

    public PostWithoutOwnerDTO getStory(long id) {
        Post story = findStory(id);
        return modelMapper.map(story, PostWithoutOwnerDTO.class);
    }

    public PostWithoutOwnerDTO updateStory(long posId, PostUpdateDTO storyUpdate, long userId) {
        Post story = findStory(posId);
        checkPermission(userId, story);
        story.setCaption(storyUpdate.getCaption());
        story.getHashtags().clear();
        story.getPersonTags().clear();
        addHashtags(story);
        addPersonTags(story);
        postRepository.save(story);
        return modelMapper.map(story, PostWithoutOwnerDTO.class);
    }

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
    
    public int likeStory(long id, long userId) {
        Post story = findPost(id);
        User user = getUserById(userId);
    
        if (story.getLikes().contains(user)) {
            story.getLikes().remove(getUserById(userId));
        } else {
            story.getLikes().add(getUserById(userId));
        }
        story.getLikes().forEach(a ->
                System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
        userRepository.save(getUserById(userId));
        story.getLikes().forEach(a ->
                System.out.println((modelMapper.map(a, UserWithoutPostsDTO.class)).getEmail()));
        return story.getLikes().size();
    }
}
