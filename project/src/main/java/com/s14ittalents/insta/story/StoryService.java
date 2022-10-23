package com.s14ittalents.insta.story;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.content.Content;
import com.s14ittalents.insta.exception.BadRequestException;
import com.s14ittalents.insta.post.*;
import com.s14ittalents.insta.util.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import static com.s14ittalents.insta.exception.Constant.YOU_CAN_ONLY_CHOOSE_10_OR_FEWER_FILES;

@Service
public class StoryService extends AbstractService {
    public PostWithoutOwnerDTO createStory(PostCreateDTO postCreateDTO, long userId) {
        Post post = new Post();
        post.setCaption(postCreateDTO.getCaption());
        post.setOwner(getUserById(userId));
        post.setCreatedTime(LocalDateTime.now());
        post.setExpirationTime(LocalDateTime.now().plusDays(1));
        addHashtags(post);
        addPersonTags(post);
        Post createdPost = postRepository.save(post);
        List<MultipartFile> files = postCreateDTO.getContents();
        if (postCreateDTO.getContents() != null) {
            if (postCreateDTO.getContents().size() > 10) {
                throw new BadRequestException(YOU_CAN_ONLY_CHOOSE_10_OR_FEWER_FILES);
            }
            List<Content> contents = uploadFiles(files, userId, createdPost);
            List<Content> createdContents = contentRepository.saveAll(contents);
            createdPost.setContents(createdContents);
        }
        return modelMapper.map(createdPost, PostWithoutOwnerDTO.class);
    }


    public PostWithoutOwnerDTO getStory(long id) {
        Post post = findStory(id);
        return modelMapper.map(post, PostWithoutOwnerDTO.class);
    }

    public PostWithoutOwnerDTO updateStory(long posId, PostUpdateDTO postUpdate, long userId) {
        Post post = findStory(posId);
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
    public boolean deleteStory(long postId, long userId) {
        Post post = findStory(postId);
        checkPermission(userId, post);
        List<Comment> comments = commentRepository.findByPostId(postId).stream().toList();
        comments.forEach(comment -> comment.setDeleted(true));
        commentRepository.saveAll(comments);
        post.setDeleted(true);
        post.setCaption("deleted at" + LocalDateTime.now());
        postRepository.save(post);
        return true;
    }
}
