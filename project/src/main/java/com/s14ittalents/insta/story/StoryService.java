package com.s14ittalents.insta.story;

import com.s14ittalents.insta.comment.Comment;
import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.post.PostRepository;
import com.s14ittalents.insta.post.PostUpdateDTO;
import com.s14ittalents.insta.post.PostWithoutOwnerDTO;
import com.s14ittalents.insta.user.User;
import com.s14ittalents.insta.user.UserRepository;
import com.s14ittalents.insta.util.AbstractController;
import com.s14ittalents.insta.util.AbstractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.USER_NOT_FOUND;

@Service
public class StoryService extends AbstractService {
    public PostWithoutOwnerDTO createStory(Post post, long userId) {
        post.setOwner(getUserById(userId));
        post.setCreatedTime(LocalDateTime.now());
        post.setExpirationTime(LocalDateTime.now().plusDays(1));
        return modelMapper.map(postRepository.save(post), PostWithoutOwnerDTO.class);
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
